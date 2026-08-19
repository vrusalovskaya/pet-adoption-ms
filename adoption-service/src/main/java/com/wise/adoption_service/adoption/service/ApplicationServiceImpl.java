package com.wise.adoption_service.adoption.service;

import com.wise.adoption_service.adoption.client.CatalogClient;
import com.wise.adoption_service.adoption.client.UserClient;
import com.wise.adoption_service.adoption.common.ApplicationStatus;
import com.wise.adoption_service.adoption.domain.Application;
import com.wise.adoption_service.adoption.domain.CreateApplicationCommand;
import com.wise.adoption_service.adoption.domain.RejectionCommand;
import com.wise.adoption_service.adoption.exception.ApplicationAccessDeniedException;
import com.wise.adoption_service.adoption.exception.ApplicationNotFoundException;
import com.wise.adoption_service.adoption.exception.ApplicationNotPendingException;
import com.wise.adoption_service.adoption.mapper.ApplicationEntityMapper;
import com.wise.adoption_service.adoption.persistence.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService, DeleteApplicationsForDeletedUser {

    private final ApplicationRepository applicationRepository;
    private final CatalogClient catalogClient;
    private final UserClient userClient;
    private final ApplicationEntityMapper entityMapper;
    private final ApplicationRevokedOutboxRepository applicationRevokedOutboxRepository;
    private final Clock clock;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Application create(CreateApplicationCommand command) {
        userClient.ensureExists(command.applicantId());
        catalogClient.ensureExists(command.animalId());

        ApplicationEntity applicationEntity = entityMapper.toEntity(command);
        applicationEntity.setStatus(ApplicationStatus.PENDING);
        ApplicationEntity saved = saveAndRefresh(applicationEntity);
        return entityMapper.toModel(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Application> getAllByApplicant(Long applicantId, Pageable pageable) {
        return applicationRepository.findByApplicantId(applicantId, pageable).map(entityMapper::toModel);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Application> getAll(ApplicationStatus status, Long animalId, Long applicantId,
                                    Pageable pageable) {
        Specification<ApplicationEntity> specification = buildSpecification(
                status,
                animalId,
                applicantId);

        return applicationRepository.findAll(specification, pageable).map(entityMapper::toModel);
    }

    @Override
    @Transactional(readOnly = true)
    public Application getForAdmin(Long id) {
        ApplicationEntity applicationEntity = getEntityById(id);
        return entityMapper.toModel(applicationEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Application getForUser(Long id, Long userId) {
        ApplicationEntity applicationEntity = getEntityById(id);
        validateOwnership(applicationEntity, userId);
        return entityMapper.toModel(applicationEntity);
    }

    @Override
    @Transactional
    public Application approve(Long id) {
        ApplicationEntity applicationEntity = getEntityById(id);
        validateStatusIsPending(applicationEntity);

        catalogClient.reserveIfAvailable(applicationEntity.getAnimalId());
        applicationEntity.setStatus(ApplicationStatus.APPROVED);
        saveAndRefresh(applicationEntity);

        return entityMapper.toModel(applicationEntity);
    }

    @Override
    @Transactional
    public Application reject(RejectionCommand command) {
        ApplicationEntity applicationEntity = getEntityById(command.id());
        validateStatusIsPending(applicationEntity);

        applicationEntity.setStatus(ApplicationStatus.REJECTED);
        applicationEntity.setDecisionComment(command.decisionComment());
        saveAndRefresh(applicationEntity);

        return entityMapper.toModel(applicationEntity);
    }

    @Override
    @Transactional
    public Application cancel(Long id, Long userId) {
        ApplicationEntity applicationEntity = getEntityById(id);
        validateOwnership(applicationEntity, userId);
        validateStatusIsPending(applicationEntity);

        applicationEntity.setStatus(ApplicationStatus.CANCELLED);
        saveAndRefresh(applicationEntity);

        return entityMapper.toModel(applicationEntity);
    }

    @Override
    @Transactional
    public void deleteByApplicantId(Long applicantId) {
        List<ApplicationEntity> approvedEntities = applicationRepository.findApprovedByApplicantId(applicantId);
        applicationRepository.deleteByApplicantId(applicantId);
        for (ApplicationEntity application : approvedEntities) {
            applicationRevokedOutboxRepository.save(new ApplicationRevokedOutboxEntity(
                    applicantId, application.getAnimalId(), clock.instant()));
        }
    }

    private ApplicationEntity saveAndRefresh(ApplicationEntity applicationEntity) {
        ApplicationEntity saved = applicationRepository.save(applicationEntity);

        entityManager.flush();
        entityManager.refresh(saved);

        return saved;
    }

    private Specification<ApplicationEntity> buildSpecification(ApplicationStatus status, Long animalId, Long applicantId) {
        return Specification
                .where(ApplicationSpecifications.statusEquals(status))
                .and(ApplicationSpecifications.animalIdEquals(animalId))
                .and(ApplicationSpecifications.applicantIdEquals(applicantId));
    }

    private ApplicationEntity getEntityById(Long id) {
        return applicationRepository.findById(id).orElseThrow(() -> new ApplicationNotFoundException(id));
    }

    private void validateStatusIsPending(ApplicationEntity applicationEntity) {
        if (applicationEntity.getStatus() != ApplicationStatus.PENDING) {
            throw new ApplicationNotPendingException(applicationEntity.getStatus());
        }
    }

    private void validateOwnership(ApplicationEntity applicationEntity, Long userId) {
        if (!Objects.equals(applicationEntity.getApplicantId(), userId)) {
            throw new ApplicationAccessDeniedException();
        }
    }
}

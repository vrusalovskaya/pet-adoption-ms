package com.wise.adoption_service.adoption.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplicationRepository extends
        JpaRepository<ApplicationEntity, Long>,
        JpaSpecificationExecutor<ApplicationEntity> {

    Page<ApplicationEntity> findByApplicantId(Long applicantId, Pageable pageable);

    @Query("""
        select application from ApplicationEntity application
        where application.applicantId = :applicantId and application.status = 'APPROVED'
        """)
    List<ApplicationEntity> findApprovedByApplicantId(Long applicantId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
        delete from ApplicationEntity application
        where application.applicantId = :applicantId
        """)
    void deleteByApplicantId(@Param("applicantId") Long applicantId);
}

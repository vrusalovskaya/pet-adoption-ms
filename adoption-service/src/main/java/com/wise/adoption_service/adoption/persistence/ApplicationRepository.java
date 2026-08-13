package com.wise.adoption_service.adoption.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ApplicationRepository extends
        JpaRepository<ApplicationEntity, Long>,
        JpaSpecificationExecutor<ApplicationEntity> {

    Page<ApplicationEntity> findByApplicantId(Long applicantId, Pageable pageable);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
        delete from ApplicationEntity application
        where application.applicantId = :applicantId
        """)
    int deleteByApplicantId(@Param("applicantId") Long applicantId);
}

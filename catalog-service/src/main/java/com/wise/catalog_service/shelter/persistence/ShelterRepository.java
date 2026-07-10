package com.wise.catalog_service.shelter.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ShelterRepository extends
        JpaRepository<ShelterEntity, Long>,
        JpaSpecificationExecutor<ShelterEntity> {
}

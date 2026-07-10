package com.wise.catalog_service.shelter.service;

import com.wise.catalog_service.shelter.domain.CreateShelterCommand;
import com.wise.catalog_service.shelter.domain.UpdateShelterCommand;
import com.wise.catalog_service.shelter.domain.Shelter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShelterService {
    Page<Shelter> getAll(String city, Boolean verified, Pageable pageable);

    Shelter get(long id);

    Shelter create(CreateShelterCommand shelter);

    Shelter update(UpdateShelterCommand shelter);

    Shelter verify(Long id);

    void delete(Long id);
}

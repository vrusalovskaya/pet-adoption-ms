package com.wise.catalog_service.animal.service;

import com.wise.catalog_service.animal.common.AnimalStatus;
import com.wise.catalog_service.animal.common.Species;
import com.wise.catalog_service.animal.domain.Animal;
import com.wise.catalog_service.animal.domain.CreateAnimalCommand;
import com.wise.catalog_service.animal.domain.UpdateAnimalCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnimalService {
    Animal get(Long id);

    Page<Animal> getAll(Species species, AnimalStatus status, Long shelterId, Pageable pageable);

    Animal create(CreateAnimalCommand command);

    Animal update(UpdateAnimalCommand command);

    Animal setStatus(Long id, AnimalStatus status);

    void delete(Long id);

    void reserveIfAvailable(Long id);
}

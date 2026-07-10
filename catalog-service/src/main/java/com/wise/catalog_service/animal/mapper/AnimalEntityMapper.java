package com.wise.catalog_service.animal.mapper;

import com.wise.catalog_service.animal.domain.Animal;
import com.wise.catalog_service.animal.domain.CreateAnimalCommand;
import com.wise.catalog_service.animal.domain.UpdateAnimalCommand;
import com.wise.catalog_service.animal.persistence.AnimalEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AnimalEntityMapper {
    @Mapping(target = "shelterEntity.id", source = "shelterId")
    AnimalEntity toEntity(CreateAnimalCommand command);

    @Mapping(target = "shelterId", source = "shelterEntity.id")
    Animal toModel(AnimalEntity animalEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "photoMetadata", ignore = true)
    void updateEntity(UpdateAnimalCommand command, @MappingTarget AnimalEntity entity);
}

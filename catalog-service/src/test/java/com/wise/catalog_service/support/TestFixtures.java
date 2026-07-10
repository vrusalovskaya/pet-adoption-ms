package com.wise.catalog_service.support;

import com.wise.catalog_service.animal.common.AnimalStatus;
import com.wise.catalog_service.animal.common.Gender;
import com.wise.catalog_service.animal.common.Species;
import com.wise.catalog_service.animal.domain.Animal;
import com.wise.catalog_service.animal.domain.CreateAnimalCommand;
import com.wise.catalog_service.animal.domain.UpdateAnimalCommand;
import com.wise.catalog_service.animal.persistence.AnimalEntity;
import com.wise.catalog_service.security.Role;
import com.wise.catalog_service.security.SecurityUser;
import com.wise.catalog_service.shelter.domain.Shelter;
import com.wise.catalog_service.shelter.domain.UpdateShelterCommand;
import com.wise.catalog_service.shelter.persistence.ShelterEntity;

import java.time.Instant;

public final class TestFixtures {

    public static final Instant NOW = Instant.parse("2026-01-01T12:00:00.00Z");

    private TestFixtures() {
    }

    public static SecurityUser securityUser(Long id, String email, Role role) {
        return new SecurityUser(id, email, "hashed-secret", "Jane", "Doe", role);
    }

    public static ShelterEntity shelterEntity(Long id) {
        ShelterEntity entity = new ShelterEntity();
        entity.setId(id);
        entity.setName("Happy Tails");
        entity.setCity("Springfield");
        entity.setAddress("1 Main St");
        entity.setContactEmail("shelter@example.com");
        entity.setContactPhone("+15557654321");
        entity.setDescription("A cozy shelter");
        entity.setVerified(false);
        entity.setCreatedAt(NOW);
        return entity;
    }

    public static Shelter shelter(Long id) {
        return new Shelter(id, "Happy Tails", "Springfield", "1 Main St",
                "shelter@example.com", "+15557654321", "A cozy shelter", false, NOW);
    }

    public static UpdateShelterCommand updateShelterCommand(Long id) {
        return new UpdateShelterCommand(id, "Happy Tails", "Springfield", "1 Main St",
                "shelter@example.com", "+15557654321", "A cozy shelter");
    }

    public static AnimalEntity animalEntity(Long id, AnimalStatus status, ShelterEntity shelter) {
        AnimalEntity entity = new AnimalEntity();
        entity.setId(id);
        entity.setShelterEntity(shelter);
        entity.setName("Rex");
        entity.setSpecies(Species.DOG);
        entity.setBreed("Labrador");
        entity.setBirthYear(2020);
        entity.setGender(Gender.MALE);
        entity.setDescription("Good boy");
        entity.setStatus(status);
        entity.setCreatedAt(NOW);
        return entity;
    }

    public static Animal animal(Long id, AnimalStatus status, Long shelterId) {
        return new Animal(id, shelterId, "Rex", Species.DOG, "Labrador", 2020,
                Gender.MALE, "Good boy", status, null, NOW);
    }

    public static CreateAnimalCommand createAnimalCommand(Long shelterId) {
        return new CreateAnimalCommand(shelterId, "Rex", Species.DOG, "Labrador",
                2020, Gender.MALE, "Good boy");
    }

    public static UpdateAnimalCommand updateAnimalCommand(Long id, Long shelterId) {
        return new UpdateAnimalCommand(id, shelterId, "Rex", Species.DOG, "Labrador",
                2020, Gender.MALE, "Good boy");
    }

}

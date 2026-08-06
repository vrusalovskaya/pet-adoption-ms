package com.wise.catalog_service.animal.controller;

import com.wise.catalog_service.animal.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/v1/animals")
@RequiredArgsConstructor
public class InternalAnimalController {
    private final AnimalService animalService;

    @RequestMapping(method = RequestMethod.HEAD, value = "/{id}")
    public ResponseEntity<Void> checkAnimalExists(@PathVariable Long id) {
        boolean exists = animalService.exists(id);
        return exists ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/reserve")
    public ResponseEntity<Void> reserve(@PathVariable Long id) {
        animalService.reserveIfAvailable(id);
        return ResponseEntity.noContent().build();
    }
}

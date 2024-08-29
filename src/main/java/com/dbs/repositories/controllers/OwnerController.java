package com.dbs.repositories.controllers;

import com.dbs.repositories.dto.OwnerRequest;
import com.dbs.repositories.dto.OwnerResponse;
import com.dbs.repositories.services.OwnerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/owners")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @PostMapping
    public ResponseEntity<OwnerResponse> createOwner(@Valid final @RequestBody OwnerRequest ownerRequestBody) {
        return ResponseEntity.ok(this.ownerService.createOwner(ownerRequestBody));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OwnerResponse> updateOwner(@Valid final @RequestBody OwnerRequest ownerRequestBody, final @PathVariable("id") Long ownerId) {
        return ResponseEntity.ok(this.ownerService.updateOwner(ownerRequestBody, ownerId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerResponse> getOwnerById(final @PathVariable("id") Long ownerId) {
        return ResponseEntity.ok(this.ownerService.getOwnerById(ownerId));
    }

    @GetMapping
    public ResponseEntity<List<OwnerResponse>> getAllOwners() {
        return ResponseEntity.ok(this.ownerService.getAllOwners());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOwner(final @PathVariable("id") Long ownerId) {
        return ResponseEntity.ok(this.ownerService.deleteOwner(ownerId));
    }
}

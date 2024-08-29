package com.dbs.repositories.controllers;

import com.dbs.repositories.dto.RepositoryRequest;
import com.dbs.repositories.dto.RepositoryResponse;
import com.dbs.repositories.services.RepositoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/repositories")
public class RepositoryController {

    private final RepositoryService repositoryService;

    public RepositoryController(final RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @PostMapping
    public ResponseEntity<RepositoryResponse> createRepository(@Valid final @RequestBody RepositoryRequest repositoryRequestBody) {
        return ResponseEntity.ok(this.repositoryService.createRepository(repositoryRequestBody));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RepositoryResponse> updateRepository(@Valid final @RequestBody RepositoryRequest repositoryRequestBody, final @PathVariable("id") Long repoId) {
        return ResponseEntity.ok(this.repositoryService.updateRepository(repositoryRequestBody, repoId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RepositoryResponse> getRepositoryById(final @PathVariable("id") Long repoId) {
        return ResponseEntity.ok(this.repositoryService.getRepositoryById(repoId));
    }

    @GetMapping
    public ResponseEntity<List<RepositoryResponse>> getAllRepositories() {
        return ResponseEntity.ok(this.repositoryService.getAllRepositories());
    }

    @GetMapping("/{ownerId}/{repositoryName}")
    public ResponseEntity<RepositoryResponse> getAllRepositoriesByOwnerAndRepositoriesName(final @PathVariable("ownerId") Long ownerId,
                                                                                           final @PathVariable("repositoryName") String repositoryName) {
        return ResponseEntity.ok(this.repositoryService.getAllRepositoriesByOwnerAndRepositoriesName(ownerId, repositoryName));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRepositoriesById(final @PathVariable("id") Long repoId) {
        return ResponseEntity.ok(this.repositoryService.deleteRepository(repoId));
    }
}

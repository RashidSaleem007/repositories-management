package com.dbs.repositories.services.impl;

import com.dbs.repositories.dto.RepositoryRequest;
import com.dbs.repositories.dto.RepositoryResponse;
import com.dbs.repositories.entities.OwnerEntity;
import com.dbs.repositories.entities.RepositoryEntity;
import com.dbs.repositories.exception.DbsException;
import com.dbs.repositories.repositories.OwnerRepo;
import com.dbs.repositories.repositories.RepositoryRepo;
import com.dbs.repositories.services.RepositoryService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RepositoryServiceImpl implements RepositoryService {

    private final RepositoryRepo repositoryRepo;
    private final OwnerRepo ownerRepo;
    private final ModelMapper modelMapper;


    public RepositoryServiceImpl(RepositoryRepo repositoryRepo, OwnerRepo ownerRepo, ModelMapper modelMapper) {
        this.repositoryRepo = repositoryRepo;
        this.ownerRepo = ownerRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public RepositoryResponse createRepository(final RepositoryRequest repositoryRequestBody) {
        final Optional<OwnerEntity> savedOwnerEntity = this.ownerRepo.findById(repositoryRequestBody.getOwnerId());
        if (savedOwnerEntity.isEmpty()) {
            throw new DbsException(HttpStatus.NOT_FOUND.value(), "Owner not found with this ID: " + repositoryRequestBody.getOwnerId());
        }
        final Boolean existsByRepositoryName = this.repositoryRepo.existsByRepositoryName(repositoryRequestBody.getRepositoryName());
        if (existsByRepositoryName) {
            throw new DbsException(HttpStatus.NOT_FOUND.value(), "Repository already exist in database with same name : " + repositoryRequestBody.getRepositoryName());
        }
        final RepositoryEntity repositoryEntity = this.modelMapper.map(repositoryRequestBody, RepositoryEntity.class);
        repositoryEntity.setCreatedAt(LocalDateTime.now());
        repositoryEntity.setOwner(savedOwnerEntity.get());
        final RepositoryEntity savedRepository = this.repositoryRepo.save(repositoryEntity);
        return this.modelMapper.map(savedRepository, RepositoryResponse.class);
    }

    @Override
    public RepositoryResponse updateRepository(final RepositoryRequest repositoryRequestBody, final Long repoId) {
        final Optional<RepositoryEntity> savedRepository = this.repositoryRepo.findById(repoId);
        if (savedRepository.isEmpty()) {
            throw new DbsException(HttpStatus.NOT_FOUND.value(), "Repository not found with this ID: " + repoId);
        }
        final Optional<RepositoryEntity> optionalRepositoryEntity = this.repositoryRepo.findByRepositoryName(repositoryRequestBody.getRepositoryName());
        if (optionalRepositoryEntity.isPresent() && !optionalRepositoryEntity.get().getRepositoryName().equalsIgnoreCase(savedRepository.get().getRepositoryName())) {
            throw new DbsException(HttpStatus.NOT_FOUND.value(), "Repository already exist in database with same name : " + repositoryRequestBody.getRepositoryName());
        }
        if (!repositoryRequestBody.getRepositoryName().isBlank()) {
            savedRepository.get().setRepositoryName(repositoryRequestBody.getRepositoryName());
        }
        if (!repositoryRequestBody.getRepositoryDesc().isBlank()) {
            savedRepository.get().setRepositoryDesc(repositoryRequestBody.getRepositoryDesc());
        }
        if (!repositoryRequestBody.getCloneUrl().isBlank()) {
            savedRepository.get().setCloneUrl(repositoryRequestBody.getCloneUrl());
        }
        if (repositoryRequestBody.getStars() != null) {
            savedRepository.get().setStars(repositoryRequestBody.getStars());
        }
        savedRepository.get().setUpdatedAt(LocalDateTime.now());
        return this.modelMapper.map(this.repositoryRepo.saveAndFlush(savedRepository.get()), RepositoryResponse.class);
    }

    @Override
    public RepositoryResponse getRepositoryById(final Long repoId) {
        final Optional<RepositoryEntity> savedRepository = this.repositoryRepo.findById(repoId);
        if (savedRepository.isEmpty()) {
            throw new DbsException(HttpStatus.NOT_FOUND.value(), "Repository not found with this ID: " + repoId);
        }
        return this.modelMapper.map(this.repositoryRepo.findById(repoId), RepositoryResponse.class);
    }

    @Override
    public List<RepositoryResponse> getAllRepositories() {
        return this.repositoryRepo.findAll()
                .stream()
                .map(repositoryEntity -> modelMapper.map(repositoryEntity, RepositoryResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Boolean deleteRepository(final Long repoId) {
        final Optional<RepositoryEntity> savedRepository = this.repositoryRepo.findById(repoId);
        if (savedRepository.isEmpty()) {
            throw new DbsException(HttpStatus.NOT_FOUND.value(), "Repository not found with this ID: " + repoId);
        }
        this.repositoryRepo.deleteById(repoId);
        return Boolean.TRUE;
    }

    @Override
    public RepositoryResponse getAllRepositoriesByOwnerAndRepositoriesName(final Long ownerId, final String repositoryName) {
        final Optional<OwnerEntity> savedOwnerEntity = this.ownerRepo.findById(ownerId);
        if (savedOwnerEntity.isEmpty()) {
            throw new DbsException(HttpStatus.NOT_FOUND.value(), "Owner not found with this ID: " + ownerId);
        }
        final Optional<RepositoryEntity> repositoryEntity = this.repositoryRepo.findByOwnerAndRepositoryName(ownerId, repositoryName);
        if (repositoryEntity.isEmpty()) {
            throw new DbsException(HttpStatus.NOT_FOUND.value(), "Repository not found with this ID: " + repositoryName);
        }
        return this.modelMapper.map(repositoryEntity, RepositoryResponse.class);
    }
}

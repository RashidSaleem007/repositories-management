package com.dbs.repositories.services.impl;

import com.dbs.repositories.dto.OwnerRequest;
import com.dbs.repositories.dto.OwnerResponse;
import com.dbs.repositories.entities.OwnerEntity;
import com.dbs.repositories.exception.DbsException;
import com.dbs.repositories.repositories.OwnerRepo;
import com.dbs.repositories.services.OwnerService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepo ownerRepo;
    private final ModelMapper modelMapper;


    public OwnerServiceImpl(OwnerRepo ownerRepo, ModelMapper modelMapper) {
        this.ownerRepo = ownerRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public OwnerResponse createOwner(final OwnerRequest ownerRequestBody) {
        final OwnerEntity ownerEntity = this.modelMapper.map(ownerRequestBody, OwnerEntity.class);
        ownerEntity.setCreatedAt(LocalDateTime.now());
        final OwnerEntity savedOwnerEntity = this.ownerRepo.save(ownerEntity);
        return this.modelMapper.map(savedOwnerEntity, OwnerResponse.class);
    }

    @Override
    public OwnerResponse updateOwner(final OwnerRequest ownerRequestBody, final Long ownerId) {
        final Optional<OwnerEntity> savedOwnerEntity = this.ownerRepo.findById(ownerId);
        if (savedOwnerEntity.isEmpty()) {
            throw new DbsException(HttpStatus.NOT_FOUND.value(), "Owner not found with this ID: " + ownerId);
        }
        if (!ownerRequestBody.getOwnerName().isBlank()) {
            savedOwnerEntity.get().setOwnerName(ownerRequestBody.getOwnerName());
        }
        if (!ownerRequestBody.getOwnerEmail().isBlank()) {
            savedOwnerEntity.get().setOwnerEmail(ownerRequestBody.getOwnerEmail());
        }
        savedOwnerEntity.get().setUpdatedAt(LocalDateTime.now());
        return this.modelMapper.map(this.ownerRepo.saveAndFlush(savedOwnerEntity.get()), OwnerResponse.class);
    }

    @Override
    public OwnerResponse getOwnerById(final Long ownerId) {
        final Optional<OwnerEntity> savedOwnerEntity = this.ownerRepo.findById(ownerId);
        if (savedOwnerEntity.isEmpty()) {
            throw new DbsException(HttpStatus.NOT_FOUND.value(), "Owner not found with this ID: " + ownerId);
        }
        return this.modelMapper.map(this.ownerRepo.findById(ownerId), OwnerResponse.class);
    }

    @Override
    public List<OwnerResponse> getAllOwners() {
        return this.ownerRepo.findAll().stream().map(ownerEntity -> modelMapper.map(ownerEntity, OwnerResponse.class)).collect(Collectors.toList());
    }

    @Override
    public Boolean deleteOwner(final Long ownerId) {
        final Optional<OwnerEntity> savedOwnerEntity = this.ownerRepo.findById(ownerId);
        if (savedOwnerEntity.isEmpty()) {
            throw new DbsException(HttpStatus.NOT_FOUND.value(), "Owner not found with this ID: " + ownerId);
        }
        this.ownerRepo.deleteById(ownerId);
        return Boolean.TRUE;
    }
}

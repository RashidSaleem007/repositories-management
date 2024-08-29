package com.dbs.repositories.services;

import com.dbs.repositories.dto.OwnerRequest;
import com.dbs.repositories.dto.OwnerResponse;
import com.dbs.repositories.entities.OwnerEntity;
import com.dbs.repositories.repositories.OwnerRepo;
import com.dbs.repositories.services.impl.OwnerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OwnerServiceTest {

    @Mock
    private OwnerRepo ownerRepo;
    @Spy
    private ModelMapper modelMapper;
    @InjectMocks
    private OwnerServiceImpl ownerService;
    private OwnerEntity ownerEntity;
    private OwnerRequest ownerRequest;
    final Long ownerId = 1L;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(OwnerServiceTest.class);
        final String name = "Rashid Saleem";
        final String email = "abc1233@gmail.com";
        ownerEntity = new OwnerEntity(name, email, null);
        ownerRequest = new OwnerRequest(name, email);
    }

    @Test
    void createOwnerTest() {
        when(this.ownerRepo.save(any())).thenReturn(ownerEntity);
        final OwnerResponse response = ownerService.createOwner(ownerRequest);
        assertEquals(ownerEntity.getOwnerEmail(), response.getOwnerEmail());
        assertEquals(ownerEntity.getOwnerEmail(), response.getOwnerEmail());
    }

    @Test
    void updateOwnerTest() {
        when(this.ownerRepo.findById(ownerId)).thenReturn(Optional.of(ownerEntity));
        when(this.ownerRepo.saveAndFlush(any())).thenReturn(ownerEntity);
        final OwnerResponse response = ownerService.updateOwner(ownerRequest, ownerId);
        assertEquals(ownerEntity.getOwnerName(), response.getOwnerName());
        assertEquals(ownerEntity.getOwnerEmail(), response.getOwnerEmail());
    }

    @Test
    void getOwnerByIdTest() {
        when(this.ownerRepo.findById(ownerId)).thenReturn(Optional.of(ownerEntity));
        final OwnerResponse response = ownerService.getOwnerById(ownerId);
        assertEquals(ownerEntity.getOwnerName(), response.getOwnerName());
        assertEquals(ownerEntity.getOwnerEmail(), response.getOwnerEmail());
    }

    @Test
    void getAllOwnersTest() {
        when(this.ownerRepo.findAll()).thenReturn(List.of(ownerEntity));
        final List<OwnerResponse> response = ownerService.getAllOwners();
        assertEquals(1, response.size());
        assertEquals(ownerEntity.getOwnerName(),  response.get(0).getOwnerName());
        assertEquals(ownerEntity.getOwnerEmail(),  response.get(0).getOwnerEmail());
    }

    @Test
    void deleteOwnerTest() {
        when(this.ownerRepo.findById(ownerId)).thenReturn(Optional.of(ownerEntity));
        doNothing().when(this.ownerRepo).deleteById(ownerId);
        final Boolean response = ownerService.deleteOwner(ownerId);
        assertEquals(Boolean.TRUE, response);
    }
}

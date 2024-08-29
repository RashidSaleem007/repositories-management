package com.dbs.repositories.services;

import com.dbs.repositories.dto.RepositoryRequest;
import com.dbs.repositories.dto.RepositoryResponse;
import com.dbs.repositories.entities.OwnerEntity;
import com.dbs.repositories.entities.RepositoryEntity;
import com.dbs.repositories.repositories.OwnerRepo;
import com.dbs.repositories.repositories.RepositoryRepo;
import com.dbs.repositories.services.impl.RepositoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RepositoryServiceTest {
    @Mock
    private RepositoryRepo repositoryRepo;
    @Mock
    private OwnerRepo ownerRepo;
    @Spy
    private ModelMapper modelMapper;
    @InjectMocks
    private RepositoryServiceImpl repositoryService;
    private RepositoryEntity repositoryEntity;
    private RepositoryRequest repositoryRequest;
    private final Long repoId = 1L;
    private final Long ownerId = 1L;
    private OwnerEntity ownerEntity;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(RepositoryServiceTest.class);
        final String name = "Rashid Saleem";
        final String email = "abc1233@gmail.com";
        final String repositoryName = "repo-123";
        final String repositoryDesc = "this is junit testing repo";
        final String cloneUrl = "localhost/repo123";
        final Integer stars = 5;
        ownerEntity = new OwnerEntity(name, email, null);
        repositoryEntity = new RepositoryEntity(repositoryName, repositoryDesc, cloneUrl, stars, ownerEntity);
        repositoryRequest = new RepositoryRequest(repositoryName, repositoryDesc, cloneUrl, stars, ownerId);
    }

    @Test
    void createRepositoryTest() {
        when(this.ownerRepo.findById(ownerId)).thenReturn(Optional.of(ownerEntity));
        when(this.repositoryRepo.existsByRepositoryName(repositoryRequest.getRepositoryName())).thenReturn(Boolean.FALSE);
        when(this.repositoryRepo.save(any())).thenReturn(repositoryEntity);
        final RepositoryResponse response = this.repositoryService.createRepository(repositoryRequest);
        assertEquals(repositoryEntity.getRepositoryDesc(), response.getRepositoryDesc());
        assertEquals(repositoryEntity.getRepositoryName(), response.getRepositoryName());
    }

    @Test
    void updateRepositoryTest() {
        when(this.repositoryRepo.findById(repoId)).thenReturn(Optional.of(repositoryEntity));
        when(this.repositoryRepo.findByRepositoryName(repositoryRequest.getRepositoryName())).thenReturn(Optional.empty());
        when(this.repositoryRepo.saveAndFlush(any())).thenReturn(repositoryEntity);
        final RepositoryResponse response = this.repositoryService.updateRepository(repositoryRequest, repoId);
        assertEquals(repositoryEntity.getRepositoryDesc(), response.getRepositoryDesc());
        assertEquals(repositoryEntity.getRepositoryName(), response.getRepositoryName());
    }

    @Test
    void getOwnerByIdTest() {
        when(this.repositoryRepo.findById(repoId)).thenReturn(Optional.of(repositoryEntity));
        final RepositoryResponse response = this.repositoryService.getRepositoryById(repoId);
        assertEquals(repositoryEntity.getRepositoryDesc(), response.getRepositoryDesc());
        assertEquals(repositoryEntity.getRepositoryName(), response.getRepositoryName());
    }

    @Test
    void getAllOwnersTest() {
        when(this.repositoryRepo.findAll()).thenReturn(List.of(repositoryEntity));
        final List<RepositoryResponse> response = this.repositoryService.getAllRepositories();
        assertEquals(1, response.size());
        assertEquals(repositoryEntity.getRepositoryDesc(), response.get(0).getRepositoryDesc());
        assertEquals(repositoryEntity.getRepositoryName(),  response.get(0).getRepositoryName());
    }

    @Test
    void deleteOwnerTest() {
        when(this.repositoryRepo.findById(repoId)).thenReturn(Optional.of(repositoryEntity));
        doNothing().when(this.repositoryRepo).deleteById(repoId);
        final Boolean response = this.repositoryService.deleteRepository(repoId);
        assertEquals(Boolean.TRUE, response);
    }
}

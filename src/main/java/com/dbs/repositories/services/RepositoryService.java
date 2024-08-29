package com.dbs.repositories.services;

import com.dbs.repositories.dto.RepositoryRequest;
import com.dbs.repositories.dto.RepositoryResponse;

import java.util.List;

public interface RepositoryService {

    RepositoryResponse createRepository(final RepositoryRequest repositoryRequestBody);

    RepositoryResponse updateRepository(final RepositoryRequest repositoryRequestBody, final Long repoId);

    RepositoryResponse getRepositoryById(final Long repoId);

    List<RepositoryResponse> getAllRepositories();

    Boolean deleteRepository(final Long repoId);

    RepositoryResponse getAllRepositoriesByOwnerAndRepositoriesName(final Long ownerId, final String repositoryName);
}

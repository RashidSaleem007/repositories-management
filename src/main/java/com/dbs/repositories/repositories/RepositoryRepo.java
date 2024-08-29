package com.dbs.repositories.repositories;

import com.dbs.repositories.entities.RepositoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RepositoryRepo extends JpaRepository<RepositoryEntity, Long> {
    @Query("Select repo from RepositoryEntity repo where repo.owner.id = :ownerId and repo.repositoryName = :repositoryName")
    Optional<RepositoryEntity> findByOwnerAndRepositoryName(final @Param("ownerId") Long ownerId, @Param("repositoryName") final String repositoryName);

    Boolean existsByRepositoryName(final String repositoryName);

    Optional<RepositoryEntity> findByRepositoryName(final String repositoryName);

}

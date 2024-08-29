package com.dbs.repositories.repositories;

import com.dbs.repositories.entities.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepo extends JpaRepository<OwnerEntity, Long> {
}

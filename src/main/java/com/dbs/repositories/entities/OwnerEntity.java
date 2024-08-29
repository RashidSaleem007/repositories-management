package com.dbs.repositories.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "t_owners")
public class OwnerEntity extends BaseEntity {

    @Column(name = "c_owner_name", nullable = false)
    private String ownerName;
    @Column(name = "c_owner_email", nullable = false)
    private String ownerEmail;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RepositoryEntity> repositories;
}

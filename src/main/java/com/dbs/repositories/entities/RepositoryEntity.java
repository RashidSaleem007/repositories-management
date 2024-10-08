package com.dbs.repositories.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_repositories")
public class RepositoryEntity extends BaseEntity {
    @Column(name = "c_repository_name")
    private String repositoryName;
    @Column(name = "c_repository_desc")
    private String repositoryDesc;
    @Column(name = "c_clone_url")
    private String cloneUrl;
    @Column(name = "c_stars")
    private Integer stars;
    @ManyToOne
    @JoinColumn(name = "c_owner_id")
    private OwnerEntity owner;
}

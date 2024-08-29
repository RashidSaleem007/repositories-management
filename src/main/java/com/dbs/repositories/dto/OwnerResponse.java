package com.dbs.repositories.dto;

import com.dbs.repositories.entities.RepositoryEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OwnerResponse {
    private Long id;
    private String ownerName;
    private String ownerEmail;
    private List<RepositoryResponse> repositories;
    private LocalDateTime createdAt;
}

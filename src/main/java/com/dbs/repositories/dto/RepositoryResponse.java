package com.dbs.repositories.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RepositoryResponse {
    private Long id;
    private String repositoryName;
    private String repositoryDesc;
    private String cloneUrl;
    private Integer stars;
    private LocalDateTime createdAt;
}

package com.dbs.repositories.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepositoryRequest {
    @NotBlank(message = "name can't be null or empty")
    private String repositoryName;
    private String repositoryDesc;
    @NotBlank(message = "clone url can't be null or empty")
    private String cloneUrl;
    private Integer stars;
    @NotNull(message = "owner id can't be null")
    private Long ownerId;

}

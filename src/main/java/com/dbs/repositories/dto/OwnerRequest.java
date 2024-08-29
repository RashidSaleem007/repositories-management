package com.dbs.repositories.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerRequest {
    @NotBlank(message = "name can't be null or empty")
    private String ownerName;
    @NotBlank(message = "email can't be null or empty ")
    @Email(message = "please provide valid email")
    private String ownerEmail;
}

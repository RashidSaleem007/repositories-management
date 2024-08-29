package com.dbs.repositories.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private int statusCode;
    private String message;
    private Map<String, String> subErrors;


    public ErrorResponse(int statusCode, String message) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public ErrorResponse(int statusCode, Map<String, String> subErrors, String message) {
        this.statusCode = statusCode;
        this.subErrors = subErrors;
        this.message = message;
    }
}

package com.dbs.repositories.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DbsException extends RuntimeException {
    private ErrorResponse errorResponse;

    public DbsException(int statusCode, String message) {
        super(message);
        errorResponse = new ErrorResponse(statusCode, message);
    }
}

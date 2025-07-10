package com.hoangnam25.hnam_courseware.exception;

public class BadRequestException extends GenericException {
    public BadRequestException(ErrorMessage error) {
        super(error.getCode(), error.getMessage(), error.getHttpStatus());
    }
}


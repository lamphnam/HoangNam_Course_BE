package com.hoangnam25.hnam_courseware.exception;

public class ConflictException extends GenericException {
    public ConflictException(ErrorMessage error) {
        super(error.getCode(), error.getMessage(), error.getHttpStatus());
    }
}

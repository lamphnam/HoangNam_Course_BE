package com.hoangnam25.hnam_courseware.exception;

public class InternalServerException extends GenericException {
    public InternalServerException(ErrorMessage error) {
        super(error.getCode(), error.getMessage(), error.getHttpStatus());
    }
}

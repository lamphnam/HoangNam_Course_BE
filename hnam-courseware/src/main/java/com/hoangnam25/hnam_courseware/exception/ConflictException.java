package com.hoangnam25.hnam_courseware.exception;

import com.hoangnam25.hnam_courseware.model.enums.ErrorMessage;

public class ConflictException extends GenericException {
    public ConflictException(ErrorMessage error) {
        super(error.getCode(), error.getMessage(), error.getHttpStatus());
    }
}

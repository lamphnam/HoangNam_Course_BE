package com.hoangnam25.hnam_courseware.exception;

import com.hoangnam25.hnam_courseware.model.enums.ErrorMessage;

public class InternalServerException extends GenericException {
    public InternalServerException(ErrorMessage error) {
        super(error.getCode(), error.getMessage(), error.getHttpStatus());
    }
}

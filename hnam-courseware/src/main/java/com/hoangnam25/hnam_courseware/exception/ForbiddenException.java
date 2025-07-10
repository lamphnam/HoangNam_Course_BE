package com.hoangnam25.hnam_courseware.exception;

import com.hoangnam25.hnam_courseware.model.enums.ErrorMessage;

public class ForbiddenException extends GenericException {
    public ForbiddenException(ErrorMessage error) {
        super(error.getCode(), error.getMessage(), error.getHttpStatus());
    }

    public ForbiddenException(ErrorMessage error, String detailMessage) {
        super(error.getCode(), detailMessage, error.getHttpStatus());
    }
}


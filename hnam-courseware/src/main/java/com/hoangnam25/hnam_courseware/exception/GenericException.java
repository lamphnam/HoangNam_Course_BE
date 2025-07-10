package com.hoangnam25.hnam_courseware.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class GenericException extends RuntimeException {
    protected int code;
    protected int status;

    public GenericException() {
        super("Internal Server Error");
    }

    public GenericException(String message) {
        super(message);
    }

    public GenericException(String message, int status) {
        super(message);
        this.status = status;
    }

    public GenericException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenericException(String message, Throwable cause, int status) {
        super(message, cause);
        this.status = status;
    }

    public GenericException(Throwable cause) {
        super(cause);
    }

    public GenericException(Throwable cause, int status) {
        super(cause);
        this.status = status;
    }

    public GenericException(int code, String message, int status) {
        super(message);
        this.status = status;
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

}

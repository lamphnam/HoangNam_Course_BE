package com.hoangnam25.hnam_courseware.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hoangnam25.hnam_courseware.exception.ErrorMessage;
import com.hoangnam25.hnam_courseware.exception.GenericException;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    private String serverDateTime = LocalDateTime.now().toString();
    private static final String SUCCESSFUL = "Successful";
    private static final String UNSUCCESSFUL = "UnSuccessful";
    private int status = 200;
    private int code;
    private String message = SUCCESSFUL;
    private String exception;
    private String traceId;
    private Object data;

    public Response(Object data) {
        this.data = data;
    }

    public Response(GenericException ex) {
        this.status = ex.getStatus();
        this.code = ex.getCode();
        this.message = UNSUCCESSFUL;
        this.exception = ex.getMessage();
        this.traceId = UUID.randomUUID().toString();
    }

    public Response(int status, String exception) {
        this.status = status;
        this.message = UNSUCCESSFUL;
        this.exception = exception;
        this.traceId = UUID.randomUUID().toString();
    }

    public Response(ErrorMessage error) {
        this.status = error.getHttpStatus();
        this.message = UNSUCCESSFUL;
        this.exception = error.getMessage();
        this.traceId = UUID.randomUUID().toString();
    }
}

package com.hoangnam25.hnam_courseware.exception.global;

import com.hoangnam25.hnam_courseware.exception.*;
import com.hoangnam25.hnam_courseware.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<Response> handleGeneric(GenericException e) {
        return ResponseEntity.status(e.getStatus())
                .body(new Response(e));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Response> handleBadRequest(BadRequestException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Response(e));
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Response> handleNotFound(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Response(e));
    }
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Response> handleException(ForbiddenException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new Response(e));
    }
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Response> handleConflict(ConflictException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new Response(e));
    }
    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<Response> handleInternalServer(InternalServerException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Response(e));
    }
}


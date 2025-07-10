package com.hoangnam25.hnam_courseware.model.enums;

import lombok.Getter;

@Getter
public enum ErrorMessage {
    USER_NOT_FOUND(404, 1001, "User not found"),
    COURSE_NOT_FOUND(404, 1002, "Course not found"),
    INTERNAL_ERROR(500, 9999, "Internal server error"),
    USERNAME_ALREADY_EXISTS(400, 1003, "Username is already in use"),
    FORBIDDEN_ROLE_REGISTER(403, 1004, "Not allowed to register with this role"),
    INVALID_USERNAME_PASSWORD(400, 1005, "Invalid username or password"),
    FORBIDDEN_AUTHORITY(403, 1006, "Forbidden authority");

    private final int httpStatus;
    private final int code;
    private final String message;

    ErrorMessage(int httpStatus, int code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

}


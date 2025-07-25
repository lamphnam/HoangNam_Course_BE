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
    FORBIDDEN_AUTHORITY(403, 1006, "Forbidden authority"),
    INVALID_ENROLLMENT(400, 1007, "Invalid enrollment"),
    ALREADY_ENROLLMENT(400, 1008, "Already enrollment"),
    ALREADY_UNENROLLMENT(400, 1009, "Already unenrollment"),
    CANNOT_CANCEL_COMPLETED_COURSE(400, 1010, "Cannot cancel a completed course"),
    ENROLLMENT_NOT_FOUND(400, 1011, "Enrollment not found"),
    NOT_ENROLLMENT(400, 1012, "Not enrollment"),
    ALREADY_REVIEWED(400, 1013, "Already reviewed"),
    REVIEW_NOT_FOUND(400, 1014, "Review not found");
    
    private final int httpStatus;
    private final int code;
    private final String message;

    ErrorMessage(int httpStatus, int code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

}


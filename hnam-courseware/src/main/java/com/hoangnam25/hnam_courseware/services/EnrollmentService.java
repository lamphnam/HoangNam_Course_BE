package com.hoangnam25.hnam_courseware.services;


import com.hoangnam25.hnam_courseware.model.dtos.EnrollmentRequestDto;
import com.hoangnam25.hnam_courseware.model.dtos.EnrollmentResponseDto;

public interface EnrollmentService {
    EnrollmentResponseDto enrollUserToCourse(EnrollmentRequestDto enrollmentRequestDto, String username);

}

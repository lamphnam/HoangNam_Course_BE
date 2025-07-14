package com.hoangnam25.hnam_courseware.services;


import com.hoangnam25.hnam_courseware.model.dtos.EnrollmentRequestDto;
import com.hoangnam25.hnam_courseware.model.dtos.EnrollmentResponseDto;
import com.hoangnam25.hnam_courseware.model.dtos.EnrollmentSearchRequestDto;
import org.springframework.data.domain.Page;

public interface EnrollmentService {
    EnrollmentResponseDto enrollUserToCourse(EnrollmentRequestDto enrollmentRequestDto, String username);

    EnrollmentResponseDto cancelEnrollment(String username, Long id);

    Page<EnrollmentResponseDto> getEnrollmentsByUserId(String username, EnrollmentSearchRequestDto request);
}


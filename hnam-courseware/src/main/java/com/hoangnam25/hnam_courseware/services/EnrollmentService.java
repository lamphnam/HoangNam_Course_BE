package com.hoangnam25.hnam_courseware.services;


import com.hoangnam25.hnam_courseware.model.dtos.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

public interface EnrollmentService {
    EnrollmentResponseDto enrollUserToCourse(EnrollmentRequestDto enrollmentRequestDto, String username);

    EnrollmentResponseDto cancelEnrollment(String username, Long id);

    Page<EnrollmentResponseDto> getEnrollmentsByUserId(String username, EnrollmentSearchRequestDto request);

    EnrollmentResponseDto getEnrollmentDetailForUser(String username, Long courseId);

    Page<StudentEnrollmentResponseDto> getEnrollmentsForCourse(String username, Long courseId, StudentSearchRequestDto request);

    StudentEnrollmentResponseDto updateEnrollment(String username, Long enrollmentId, @Valid UpdateEnrollmentRequestDto request);
}


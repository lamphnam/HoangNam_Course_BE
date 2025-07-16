package com.hoangnam25.hnam_courseware.model.dtos;

import com.hoangnam25.hnam_courseware.model.enums.EnrollmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentEnrollmentResponseDto {
    private Long enrollmentId;
    private UserResponseDto user;
    private EnrollmentStatus status;
    private BigDecimal progressPercentage;
    private LocalDateTime enrolledDate;
    private LocalDateTime completedDate;

}

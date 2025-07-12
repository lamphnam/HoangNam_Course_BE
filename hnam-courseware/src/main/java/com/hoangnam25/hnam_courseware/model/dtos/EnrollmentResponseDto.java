package com.hoangnam25.hnam_courseware.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnrollmentResponseDto {

    private Long id;

    private CourseResponseDto course;

    private UserSummaryDto user;

    private String status;

    private BigDecimal progressPercentage;

    private LocalDateTime enrolledDate;

    private LocalDateTime completedDate;
}

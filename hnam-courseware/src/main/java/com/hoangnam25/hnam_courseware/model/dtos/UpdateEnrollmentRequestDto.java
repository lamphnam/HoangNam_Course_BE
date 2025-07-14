package com.hoangnam25.hnam_courseware.model.dtos;

import com.hoangnam25.hnam_courseware.model.enums.EnrollmentStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateEnrollmentRequestDto {
    private EnrollmentStatus status;
    private BigDecimal progressPercentage;
}

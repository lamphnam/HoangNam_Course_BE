package com.hoangnam25.hnam_courseware.model.dtos;

import com.hoangnam25.hnam_courseware.model.enums.DirectionEnum;
import com.hoangnam25.hnam_courseware.model.enums.EnrollmentStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class EnrollmentSearchRequestDto {
    private int page;
    private int size;
    private DirectionEnum direction;
    private String attribute;

    private String title;
    private EnrollmentStatus status;
    private LocalDateTime enrolledDate;
    private BigDecimal progressPercentage;
}

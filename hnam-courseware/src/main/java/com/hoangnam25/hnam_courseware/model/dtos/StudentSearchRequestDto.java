package com.hoangnam25.hnam_courseware.model.dtos;

import com.hoangnam25.hnam_courseware.model.enums.DirectionEnum;
import com.hoangnam25.hnam_courseware.model.enums.EnrollmentStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentSearchRequestDto {
    private int page;
    private int size;
    private DirectionEnum direction;
    private String attribute;

    private String studentName;
    private EnrollmentStatus status;
}

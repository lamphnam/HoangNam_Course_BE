package com.hoangnam25.hnam_courseware.model.dtos;

import lombok.Data;

@Data
public class ReviewRequestDto {
    private Long courseId;
    private Integer rating;
    private String comment;
}

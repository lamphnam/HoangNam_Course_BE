package com.hoangnam25.hnam_courseware.model.dtos;

import lombok.Data;

@Data
public class ReviewUpdateRequestDto {
    private Integer rating;

    private String comment;
}


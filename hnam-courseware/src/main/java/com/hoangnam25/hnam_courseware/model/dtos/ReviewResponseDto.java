package com.hoangnam25.hnam_courseware.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewResponseDto {

    private Long id;

    private Long courseId;

    private UserSummaryDto user;

    private String comment;

    private Integer rating;

    private LocalDateTime createdDate;
}

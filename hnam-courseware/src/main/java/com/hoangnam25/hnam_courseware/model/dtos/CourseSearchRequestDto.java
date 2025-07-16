package com.hoangnam25.hnam_courseware.model.dtos;

import com.hoangnam25.hnam_courseware.model.enums.CourseDifficulty;
import com.hoangnam25.hnam_courseware.model.enums.DirectionEnum;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CourseSearchRequestDto {
    private int page;
    private int size;
    private DirectionEnum direction;
    private String attribute;

    private String title;
    private CourseDifficulty difficulty;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private BigDecimal minRating;
}

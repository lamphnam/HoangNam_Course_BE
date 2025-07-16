package com.hoangnam25.hnam_courseware.model.dtos;

import com.hoangnam25.hnam_courseware.model.enums.CourseDifficulty;
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
public class CourseResponseDto {

    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private String currency;
    private CourseDifficulty difficulty;
    private String imageUrl;
    private LocalDateTime createdDate;

    private InstructorResponse instructor;

    private BigDecimal averageRating;
    private Long reviewCount;

}


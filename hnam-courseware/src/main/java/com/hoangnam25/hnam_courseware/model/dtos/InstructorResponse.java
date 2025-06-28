package com.hoangnam25.hnam_courseware.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstructorResponse {
    private Long id;
    private String firstName;
    private String imageUrl;
}

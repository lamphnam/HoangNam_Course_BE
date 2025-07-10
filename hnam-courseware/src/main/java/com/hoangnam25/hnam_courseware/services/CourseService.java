package com.hoangnam25.hnam_courseware.services;

import com.hoangnam25.hnam_courseware.model.dtos.CourseRequestDto;
import com.hoangnam25.hnam_courseware.model.dtos.CourseResponseDto;
import com.hoangnam25.hnam_courseware.model.dtos.CourseUpdateRequestDto;
import com.hoangnam25.hnam_courseware.model.enums.DirectionEnum;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface CourseService {
    CourseResponseDto createCourse(CourseRequestDto request, String username);

    Page<CourseResponseDto> searchCourses(Integer page, Integer size, DirectionEnum direction, String attribute, String title);

    CourseResponseDto findCourseById(Long id);

    CourseResponseDto updateCourseById(Long id, CourseUpdateRequestDto request, String username);

    Map<String, String> deleteCourseById(Long id, String username);

    Page<CourseResponseDto> getInstructorCourses(String username, int page, int size);
}

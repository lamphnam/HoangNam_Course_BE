package com.hoangnam25.hnam_courseware.controller;

import com.hoangnam25.hnam_courseware.model.dtos.CourseRequestDto;
import com.hoangnam25.hnam_courseware.model.dtos.CourseResponseDto;
import com.hoangnam25.hnam_courseware.model.dtos.CourseUpdateRequestDto;
import com.hoangnam25.hnam_courseware.model.enums.DirectionEnum;
import com.hoangnam25.hnam_courseware.services.CourseService;
import com.hoangnam25.hnam_courseware.services.impl.CourseServiceImpl;
import com.hoangnam25.hnam_courseware.utils.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Validated
@RequestMapping("api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseServiceImpl courseService) {
        this.courseService = courseService;
    }

    @PostMapping()
    public ResponseEntity<CourseResponseDto> createCourse(@RequestBody @Valid CourseRequestDto request) {
        String username = SecurityUtil.getUsername();
        CourseResponseDto response = courseService.createCourse(request, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    public ResponseEntity<Page<CourseResponseDto>> listCourses(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "20") Integer size,
            @RequestParam(name = "direction", required = false, defaultValue = "ASC") DirectionEnum direction,
            @RequestParam(name = "attribute", required = false, defaultValue = "createdDate") String attribute,
            @RequestParam(name = "title", required = false) String title) {
        Page<CourseResponseDto> courses = courseService.searchCourses(page, size, direction, attribute, title);
        return ResponseEntity.status(HttpStatus.OK).body(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDto> getCourseById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.findCourseById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CourseResponseDto> updateCourse(@PathVariable("id") Long id, @RequestBody @Valid CourseUpdateRequestDto request) {
        String username = SecurityUtil.getUsername();
        CourseResponseDto response = courseService.updateCourseById(id, request, username);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteCourse(@PathVariable("id") Long id) {
        String username = SecurityUtil.getUsername();
        Map<String, String> response = courseService.deleteCourseById(id, username);
        return ResponseEntity.ok(response);
    }
}

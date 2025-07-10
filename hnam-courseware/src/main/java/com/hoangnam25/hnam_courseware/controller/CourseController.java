package com.hoangnam25.hnam_courseware.controller;

import com.hoangnam25.hnam_courseware.model.dtos.CourseRequestDto;
import com.hoangnam25.hnam_courseware.model.dtos.CourseUpdateRequestDto;
import com.hoangnam25.hnam_courseware.model.enums.DirectionEnum;
import com.hoangnam25.hnam_courseware.response.Response;
import com.hoangnam25.hnam_courseware.services.CourseService;
import com.hoangnam25.hnam_courseware.services.impl.CourseServiceImpl;
import com.hoangnam25.hnam_courseware.utils.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseServiceImpl courseService) {
        this.courseService = courseService;
    }

    @PostMapping()
    public Response createCourse(@RequestBody @Valid CourseRequestDto request) {
        String username = SecurityUtil.getUsername();
        return new Response(courseService.createCourse(request, username));
    }

    @GetMapping()
    public Response listCourses(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "20") Integer size,
            @RequestParam(name = "direction", required = false, defaultValue = "ASC") DirectionEnum direction,
            @RequestParam(name = "attribute", required = false, defaultValue = "createdDate") String attribute,
            @RequestParam(name = "title", required = false) String title) {
        return new Response(courseService.searchCourses(page, size, direction, attribute, title));
    }

    @GetMapping("/instructor")
    public Response getInstructorCourses(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size) {
        String username = SecurityUtil.getUsername();
        return new Response(courseService.getInstructorCourses(username, page, size));
    }

    @GetMapping("/{id}")
    public Response getCourseById(@PathVariable("id") Long id) {
        return new Response(courseService.findCourseById(id));
    }

    @PatchMapping("/{id}")
    public Response updateCourse(@PathVariable("id") Long id, @RequestBody @Valid CourseUpdateRequestDto request) {
        String username = SecurityUtil.getUsername();
        return new Response(courseService.updateCourseById(id, request, username));
    }

    @DeleteMapping("/{id}")
    public Response deleteCourse(@PathVariable("id") Long id) {
        String username = SecurityUtil.getUsername();
        return new Response(courseService.deleteCourseById(id, username));
    }
}

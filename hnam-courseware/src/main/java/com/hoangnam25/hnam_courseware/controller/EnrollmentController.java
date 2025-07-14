package com.hoangnam25.hnam_courseware.controller;

import com.hoangnam25.hnam_courseware.model.dtos.EnrollmentRequestDto;
import com.hoangnam25.hnam_courseware.model.dtos.EnrollmentSearchRequestDto;
import com.hoangnam25.hnam_courseware.model.enums.DirectionEnum;
import com.hoangnam25.hnam_courseware.model.enums.EnrollmentStatus;
import com.hoangnam25.hnam_courseware.response.Response;
import com.hoangnam25.hnam_courseware.services.EnrollmentService;
import com.hoangnam25.hnam_courseware.utils.SecurityUtil;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping()
    public Response enrollCourse(@RequestBody EnrollmentRequestDto request) {
        String username = SecurityUtil.getUsername();
        return new Response(enrollmentService.enrollUserToCourse(request, username));
    }

    @DeleteMapping("/{courseId}")
    public Response unenrollCourse(@PathVariable Long courseId) {
        String username = SecurityUtil.getUsername();
        return new Response(enrollmentService.cancelEnrollment(username, courseId));
    }

    @GetMapping("/me")
    public Response getMyEnrollments(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "20") Integer size,
            @RequestParam(name = "direction", required = false, defaultValue = "DESC") DirectionEnum direction,
            @RequestParam(name = "attribute", required = false, defaultValue = "enrolledDate") String attribute,
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "status", required = false) EnrollmentStatus status,
            @RequestParam(name = "enrolledDate", required = false) LocalDateTime enrolledDate,
            @RequestParam(name = "progressPercentage", required = false) BigDecimal progressPercentage
    ) {
        String username = SecurityUtil.getUsername();
        return new Response(enrollmentService.getEnrollmentsByUserId(username,
                EnrollmentSearchRequestDto.builder()
                        .page(page)
                        .size(size)
                        .direction(direction)
                        .attribute(attribute)
                        .title(title)
                        .status(status)
                        .enrolledDate(enrolledDate)
                        .progressPercentage(progressPercentage)
                        .build()));
    }
}

package com.hoangnam25.hnam_courseware.controller;

import com.hoangnam25.hnam_courseware.model.dtos.EnrollmentRequestDto;
import com.hoangnam25.hnam_courseware.response.Response;
import com.hoangnam25.hnam_courseware.services.EnrollmentService;
import com.hoangnam25.hnam_courseware.utils.SecurityUtil;
import org.springframework.web.bind.annotation.*;

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

}

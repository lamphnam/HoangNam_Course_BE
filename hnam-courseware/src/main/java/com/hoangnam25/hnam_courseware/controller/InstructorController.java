package com.hoangnam25.hnam_courseware.controller;

import com.hoangnam25.hnam_courseware.model.dtos.StudentSearchRequestDto;
import com.hoangnam25.hnam_courseware.model.dtos.UpdateEnrollmentRequestDto;
import com.hoangnam25.hnam_courseware.model.enums.DirectionEnum;
import com.hoangnam25.hnam_courseware.model.enums.EnrollmentStatus;
import com.hoangnam25.hnam_courseware.response.Response;
import com.hoangnam25.hnam_courseware.services.EnrollmentService;
import com.hoangnam25.hnam_courseware.utils.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/instructor")
@PreAuthorize("hasRole('INSTRUCTOR')")
public class InstructorController {

    private final EnrollmentService enrollmentService;

    public InstructorController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("/courses/{courseId}/enrollments")
    public Response getEnrolledStudents
            (@PathVariable Long courseId,
             @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
             @RequestParam(name = "size", required = false, defaultValue = "20") Integer size,
             @RequestParam(name = "direction", required = false, defaultValue = "DESC") DirectionEnum direction,
             @RequestParam(name = "attribute", required = false, defaultValue = "enrolledDate") String attribute,
             @RequestParam(name = "studentName", required = false) String studentName,
             @RequestParam(name = "status", required = false) EnrollmentStatus status
            ) {
        String username = SecurityUtil.getUsername();
        return new Response(enrollmentService.getEnrollmentsForCourse(username, courseId,
                StudentSearchRequestDto
                        .builder()
                        .page(page)
                        .size(size)
                        .direction(direction)
                        .attribute(attribute)
                        .studentName(studentName)
                        .status(status)
                        .build()
                ));
    }
    @PatchMapping("/enrollments/{enrollmentId}")
    public Response updateEnrollment(
            @PathVariable Long enrollmentId,
            @RequestBody @Valid UpdateEnrollmentRequestDto request) {
        String username = SecurityUtil.getUsername();
        return new Response(enrollmentService.updateEnrollment(username, enrollmentId, request));
    }
}

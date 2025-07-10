package com.hoangnam25.hnam_courseware.controller;

import com.hoangnam25.hnam_courseware.model.dtos.UserRequest;
import com.hoangnam25.hnam_courseware.response.Response;
import com.hoangnam25.hnam_courseware.services.UserService;
import com.hoangnam25.hnam_courseware.utils.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public Response getCurrentUser() {
        String username = SecurityUtil.getUsername();
        return new Response(userService.findMe(username));
    }
    @PatchMapping("/me")
    public Response updateCurrentUser(@Valid @RequestBody UserRequest request) {
        String username = SecurityUtil.getUsername();
        return new Response(userService.updateUserInfo(username, request));
    }
}

package com.hoangnam25.hnam_courseware.controller;

import com.hoangnam25.hnam_courseware.model.dtos.UserRequest;
import com.hoangnam25.hnam_courseware.model.dtos.UserResponseDto;
import com.hoangnam25.hnam_courseware.services.UserService;
import com.hoangnam25.hnam_courseware.utils.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getCurrentUser() {
        String username = SecurityUtil.getUsername();
        return ResponseEntity.ok(userService.findMe(username));
    }
    @PutMapping("/me")
    public ResponseEntity<UserResponseDto> updateCurrentUser(@Valid @RequestBody UserRequest request) {
        String username = SecurityUtil.getUsername();
        return ResponseEntity.ok(userService.updateUserInfo(username, request));
    }
}

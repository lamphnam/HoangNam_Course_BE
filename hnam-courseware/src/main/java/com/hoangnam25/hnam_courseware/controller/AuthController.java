package com.hoangnam25.hnam_courseware.controller;

import com.hoangnam25.hnam_courseware.model.dtos.LoginRequest;
import com.hoangnam25.hnam_courseware.model.dtos.RegisterRequest;
import com.hoangnam25.hnam_courseware.response.Response;
import com.hoangnam25.hnam_courseware.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public Response registerUser(@Valid @RequestBody RegisterRequest request) {
        return new Response(authenticationService.registerUser(request));
    }

    @PostMapping("/login")
    public Response login(@Valid @RequestBody LoginRequest request) {
        return new Response(authenticationService.login(request));
    }
}

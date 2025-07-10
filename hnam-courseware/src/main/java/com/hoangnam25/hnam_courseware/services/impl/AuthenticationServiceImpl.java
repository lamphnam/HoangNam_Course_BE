package com.hoangnam25.hnam_courseware.services.impl;

import com.hoangnam25.hnam_courseware.exception.*;
import com.hoangnam25.hnam_courseware.model.dtos.LoginRequest;
import com.hoangnam25.hnam_courseware.model.dtos.RegisterRequest;
import com.hoangnam25.hnam_courseware.model.entity.Users;
import com.hoangnam25.hnam_courseware.model.enums.ErrorMessage;
import com.hoangnam25.hnam_courseware.model.enums.Role;
import com.hoangnam25.hnam_courseware.repository.UserRepository;
import com.hoangnam25.hnam_courseware.model.dtos.AuthenticationResponse;
import com.hoangnam25.hnam_courseware.services.AuthenticationService;
import com.hoangnam25.hnam_courseware.services.JwtService;
import com.hoangnam25.hnam_courseware.utils.AvatarUtils;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AuthenticationResponse registerUser(@Valid RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new BadRequestException(ErrorMessage.USERNAME_ALREADY_EXISTS);
        }

        Role role = validateAndSetRole(request.getRole());

        Users user = Users.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .role(role)
                .imageUrl(getAvatarByRole(role))
                .createdDate(LocalDateTime.now())
                .build();

        userRepository.save(user);

        return AuthenticationResponse.builder()
                .message("Register successful")
                .userId(user.getId())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .build();
    }

    private Role validateAndSetRole(Role requestRole) {
        List<Role> allowedRoles = List.of(Role.USER, Role.INSTRUCTOR);

        if (requestRole == null) {
            return Role.USER;
        }

        if (!allowedRoles.contains(requestRole)) {
            throw new ForbiddenException(
                    ErrorMessage.FORBIDDEN_ROLE_REGISTER,
                    "Not allowed to register with role: " + requestRole
            );
        }

        return requestRole;
    }

    private String getAvatarByRole(Role role) {
        return switch (role) {
            case USER -> AvatarUtils.getRandomStudentAvatar();
            case INSTRUCTOR -> AvatarUtils.getRandomInstructorAvatar();
            default -> AvatarUtils.getRandomAvatar();
        };
    }

    public AuthenticationResponse login(@Valid LoginRequest request) {
        Users user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND));
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (BadCredentialsException ex) {
            throw new BadRequestException(ErrorMessage.INVALID_USERNAME_PASSWORD);
        }

        String token = jwtService.generateToken(user.getUsername());

        return AuthenticationResponse.builder()
                .message("Login successful")
                .token(token)
                .userId(user.getId())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .build();
    }
}

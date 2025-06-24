package com.hoangnam25.hnam_courseware.services;

import com.hoangnam25.hnam_courseware.model.dtos.LoginRequest;
import com.hoangnam25.hnam_courseware.model.dtos.RegisterRequest;
import com.hoangnam25.hnam_courseware.model.entity.Users;
import com.hoangnam25.hnam_courseware.model.enums.Role;
import com.hoangnam25.hnam_courseware.repository.UserRepository;
import com.hoangnam25.hnam_courseware.response.AuthenticationResponse;
import com.hoangnam25.hnam_courseware.utils.Constants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AuthenticationResponse registerUser(@Valid RegisterRequest request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username is already in use");
        }

        Role role = request.getRole();

        List<Role> allowedRoles = List.of(Role.USER, Role.INSTRUCTOR);
        if (role == null) {
            role = Role.USER;
        } else if (!allowedRoles.contains(role)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not allowed to register with role: " + role);
        }

        Users user = Users.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .role(role)
                .createdDate(LocalDateTime.now())
                .imageUrl(randomAvatar())
                .build();

        userRepository.save(user);

        return AuthenticationResponse.builder()
                .message("Register successful")
                .userId(user.getId())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .build();
    }

    private String randomAvatar() {
        List<String> avatars = Constants.AVATARS;
        return avatars.get(ThreadLocalRandom.current().nextInt(avatars.size()));
    }

    public AuthenticationResponse login(@Valid LoginRequest request) {
        Users user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (BadCredentialsException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
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

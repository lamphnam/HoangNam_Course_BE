package com.hoangnam25.hnam_courseware.services;

import com.hoangnam25.hnam_courseware.model.dtos.RegisterRequest;
import com.hoangnam25.hnam_courseware.model.entity.Users;
import com.hoangnam25.hnam_courseware.model.enums.Role;
import com.hoangnam25.hnam_courseware.repository.UserRepository;
import com.hoangnam25.hnam_courseware.response.AuthenticationResponse;
import com.hoangnam25.hnam_courseware.utils.Constants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthenticationResponse registerUser(@Valid RegisterRequest request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username is already in use");
        }

        Role role = request.getRole() != null ? request.getRole() : Role.USER;

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
}

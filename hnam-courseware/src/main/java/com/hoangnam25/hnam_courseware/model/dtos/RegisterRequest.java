package com.hoangnam25.hnam_courseware.model.dtos;

import com.hoangnam25.hnam_courseware.model.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterRequest {
    @Pattern(regexp = "^[a-zA-Z0-9_]{3,20}$", message = "Username must contain only letters, numbers, underscores and be 3–20 characters long.")
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @Email
    private String email;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private Role role;
}


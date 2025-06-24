package com.hoangnam25.hnam_courseware.model.dtos;

import com.hoangnam25.hnam_courseware.model.enums.Role;
import lombok.Data;

@Data
public class UserResponseDto {

    private String firstName;

    private String lastName;

    private String email;

    private Role role;

    private String imageUrl;

}

package com.hoangnam25.hnam_courseware.services.impl;

import com.hoangnam25.hnam_courseware.converter.UserConverter;
import com.hoangnam25.hnam_courseware.exception.ErrorMessage;
import com.hoangnam25.hnam_courseware.exception.NotFoundException;
import com.hoangnam25.hnam_courseware.model.dtos.UserRequest;
import com.hoangnam25.hnam_courseware.model.dtos.UserResponseDto;
import com.hoangnam25.hnam_courseware.model.entity.Users;
import com.hoangnam25.hnam_courseware.repository.UserRepository;
import com.hoangnam25.hnam_courseware.services.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserConverter userConverter;

    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    public UserResponseDto findMe(String username) {
        Optional<Users> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            throw new NotFoundException(ErrorMessage.USER_NOT_FOUND, "User not found");
        }

        return userConverter.convertToDTO(userOptional.get());
    }

    public UserResponseDto updateUserInfo(String username, UserRequest request) {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND, "User not found"));

        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        if (request.getImageUrl() != null) {
            user.setImageUrl(request.getImageUrl());
        }

        userRepository.save(user);

        return userConverter.convertToDTO(user);
    }

}

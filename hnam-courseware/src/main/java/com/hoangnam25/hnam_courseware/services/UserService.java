package com.hoangnam25.hnam_courseware.services;

import com.hoangnam25.hnam_courseware.converter.UserConverter;
import com.hoangnam25.hnam_courseware.model.dtos.UserRequest;
import com.hoangnam25.hnam_courseware.model.dtos.UserResponseDto;
import com.hoangnam25.hnam_courseware.model.entity.Users;
import com.hoangnam25.hnam_courseware.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public interface UserService {
    UserResponseDto findMe(String username);

    UserResponseDto updateUserInfo(String username, UserRequest request);

}

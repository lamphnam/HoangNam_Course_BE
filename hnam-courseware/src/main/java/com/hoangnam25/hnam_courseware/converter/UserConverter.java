package com.hoangnam25.hnam_courseware.converter;

import com.hoangnam25.hnam_courseware.model.dtos.UserResponseDto;
import com.hoangnam25.hnam_courseware.model.entity.Users;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component("userConverter")
public class UserConverter extends SuperConverter<UserResponseDto, Users> {


    private final ModelMapper modelMapper;

    public UserConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UserResponseDto convertToDTO(Users entity) {
        return modelMapper.map(entity, UserResponseDto.class);
    }

    @Override
    public Users convertToEntity(UserResponseDto dto) {
        return modelMapper.map(dto, Users.class);
    }
}

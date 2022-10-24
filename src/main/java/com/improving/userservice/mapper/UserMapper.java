package com.improving.userservice.mapper;

import com.improving.userservice.dto.UserCreationDTO;
import com.improving.userservice.dto.UserDTO;
import com.improving.userservice.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toUserDto(User user) {
        return new UserDTO(user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getDateOfBirth());
    }

    public User toUser(UserCreationDTO userDTO) {
        return new User(userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getDateOfBirth());
    }
}

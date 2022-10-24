package com.improving.userservice.service;

import com.improving.userservice.dto.UserCreationDTO;
import com.improving.userservice.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDTO> getAll();
    Optional<UserDTO> getById(long userId);
    UserDTO save(UserCreationDTO userCreationDTO);
    Optional<UserDTO> update(UserCreationDTO userCreationDTO, long userId);
    Boolean delete(long userId);
}

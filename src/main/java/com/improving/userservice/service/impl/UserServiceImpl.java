package com.improving.userservice.service.impl;

import com.improving.userservice.dto.UserCreationDTO;
import com.improving.userservice.dto.UserDTO;
import com.improving.userservice.entity.User;
import com.improving.userservice.mapper.UserMapper;
import com.improving.userservice.repository.UserRepository;
import com.improving.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Override
    public List<UserDTO> getAll() {
        return userRepository.findByStatusTrue()
                .stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> getById(long userId) {
        return userRepository.findByUserIdAndStatusTrue(userId).map(user -> userMapper.toUserDto(user));
    }

    @Override
    public UserDTO save(UserCreationDTO userCreationDTO) {
        User newUser = userMapper.toUser(userCreationDTO);
        return userMapper.toUserDto(userRepository.save(newUser));
    }

    @Override
    public Optional<UserDTO> update(UserCreationDTO userCreationDTO, long userId) {
        return userRepository.findByUserIdAndStatusTrue(userId).map(user -> {
            user.setFirstName(userCreationDTO.getFirstName());
            user.setLastName(userCreationDTO.getLastName());
            user.setDateOfBirth(userCreationDTO.getDateOfBirth());
            User updatedUser = userRepository.save(user);
            return userMapper.toUserDto(updatedUser);
        });
    }

    @Override
    public Boolean delete(long userId) {
       return userRepository.findByUserIdAndStatusTrue(userId).map(user -> {
            user.setStatus(false);
            userRepository.save(user);
            return true;
    }).orElse(false);
    }
}

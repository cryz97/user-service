package com.improving.userservice.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;

import com.improving.userservice.dto.UserCreationDTO;
import com.improving.userservice.dto.UserDTO;
import com.improving.userservice.entity.User;
import com.improving.userservice.mapper.UserMapper;
import com.improving.userservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    UserCreationDTO userCreationDTO;
    User user;

    @BeforeEach
    void setUp() {
        userCreationDTO = new UserCreationDTO("Chris", "Macias",
                LocalDate.of(1997, Month.APRIL, 19));

        user = new User("Chris", "Macias",
                LocalDate.of(1997, Month.APRIL, 19));
        user.setUserId(1L);

    }

    @Test
    void getAll() {

        List<User> userList = new ArrayList<>();
        userList.add(user);
        Mockito.when(userRepository.findByStatusTrue()).thenReturn(userList);

        List<UserDTO> returnedList = userService.getAll();

        assertThat(returnedList).isNotNull();
        assertThat(returnedList).isNotEmpty();
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void getById() {

        Mockito.when(userMapper.toUserDto(any(User.class))).thenReturn(
                new UserDTO(user.getUserId(), user.getFirstName(), user.getLastName(), user.getDateOfBirth()));
        Mockito.when(userRepository.findByUserIdAndStatusTrue(1L)).thenReturn(Optional.of(user));

        Optional<UserDTO> returnedUser = userService.getById(1L);

        assertThat(returnedUser).isNotNull();
        assertThat(returnedUser.get().getUserId()).isEqualTo(1L);
        assertThat(returnedUser.get().getFirstName()).isEqualTo("Chris");
        assertThat(returnedUser.get().getLastName()).isEqualTo("Macias");
        verifyNoMoreInteractions(userRepository);

    }

    @Test
    void save() {
        Mockito.when(userMapper.toUserDto(any(User.class))).thenReturn(
                new UserDTO(user.getUserId(), user.getFirstName(), user.getLastName(), user.getDateOfBirth()));
        Mockito.when(userMapper.toUser(any(UserCreationDTO.class))).thenReturn(user);

        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);

        var userReturned = userService.save(new UserCreationDTO());

        assertThat(userReturned).isNotNull();
        assertThat(userReturned.getUserId()).isEqualTo(userReturned.getUserId());
        verify(userRepository, times(1)).save(any(User.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void update() {


        Mockito.when(userRepository.findByUserIdAndStatusTrue(1L)).thenReturn(Optional.of(user));
        user.setFirstName("Christian");
        user.setLastName("Cuevas");

        Mockito.when(userMapper.toUserDto(any(User.class))).thenReturn(
                new UserDTO(user.getUserId(), user.getFirstName(), user.getLastName(), user.getDateOfBirth()));

        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);

        Optional<UserDTO> updatedUser = userService.update(
                new UserCreationDTO(user.getFirstName(), user.getLastName(), user.getDateOfBirth()), 1L);

        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.get().getUserId()).isEqualTo(1L);
        assertThat(updatedUser.get().getFirstName()).isEqualTo("Christian");
        assertThat(updatedUser.get().getLastName()).isEqualTo("Cuevas");
        verifyNoMoreInteractions(userRepository);

    }

    @Test
    void delete() {

        Mockito.when(userRepository.findByUserIdAndStatusTrue(1L)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);

        Boolean isDeleted = userService.delete(1L);

        assertThat(isDeleted).isNotNull();
        assertThat(isDeleted).isTrue();
        verifyNoMoreInteractions(userRepository);
    }
}
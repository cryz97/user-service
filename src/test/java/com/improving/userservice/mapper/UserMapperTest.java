package com.improving.userservice.mapper;

import com.improving.userservice.dto.UserCreationDTO;
import com.improving.userservice.dto.UserDTO;
import com.improving.userservice.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    void toUserDto() {

        User user = new User("Chris", "Macias",
                LocalDate.of(1997, Month.APRIL, 19));
        user.setUserId(1L);

        UserDTO userDTO = userMapper.toUserDto(user);

        Assertions.assertThat(userDTO).isNotNull();
        Assertions.assertThat(userDTO.getFirstName()).isEqualTo(user.getFirstName());
        Assertions.assertThat(userDTO.getLastName()).isEqualTo(user.getLastName());
        Assertions.assertThat(userDTO.getDateOfBirth()).isEqualTo(user.getDateOfBirth());
    }

    @Test
    void toUser() {

        UserCreationDTO userDTO = new UserCreationDTO("Christian", "Macias",
                LocalDate.of(1997, Month.APRIL, 19));

        User user = userMapper.toUser(userDTO);

        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getFirstName()).isEqualTo(userDTO.getFirstName());
        Assertions.assertThat(user.getLastName()).isEqualTo(userDTO.getLastName());
        Assertions.assertThat(user.getDateOfBirth()).isEqualTo(userDTO.getDateOfBirth());
    }
}
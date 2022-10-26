package com.improving.userservice.repository;

import com.improving.userservice.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User user = new User("Chris", "Macias", LocalDate.of(1997, Month.APRIL, 19));
        userRepository.save(user);
    }

    @Test
    void findByStatusTrue() {

        List<User> userList = userRepository.findByStatusTrue();

        Assertions.assertThat(userList).isNotNull();
        Assertions.assertThat(userList).isNotEmpty();
        Assertions.assertThat(userList.size()).isEqualTo(1);
        Assertions.assertThat(userList.get(0).getStatus()).isTrue();
    }

    @Test
    void findByUserIdAndStatusTrue() {

        userRepository.findByUserIdAndStatusTrue(1).map(user -> {
            Assertions.assertThat(user).isNotNull();
            return Assertions.assertThat(user.getStatus()).isTrue();
        });
    }
}
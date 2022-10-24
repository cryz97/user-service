package com.improving.userservice.repository;

import com.improving.userservice.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository  extends CrudRepository<User, Long> {

    List<User> findByStatusTrue();

    Optional<User> findByUserIdAndStatusTrue(long userId);


}

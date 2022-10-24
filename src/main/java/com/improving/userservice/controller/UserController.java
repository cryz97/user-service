package com.improving.userservice.controller;

import com.improving.userservice.dto.UserCreationDTO;
import com.improving.userservice.dto.UserDTO;
import com.improving.userservice.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") long userId) {
        return userService.getById(userId).map(userDTO -> new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserCreationDTO userCreationDTO){
        return new ResponseEntity<>(userService.save(userCreationDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") long userId,
                                              @RequestBody UserCreationDTO userCreationDTO) {
        return userService.update(userCreationDTO, userId)
                .map(userDTO -> new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") int userId) {
        if(userService.delete(userId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return new  ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}

package com.improving.userservice.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.improving.userservice.dto.UserCreationDTO;
import com.improving.userservice.dto.UserDTO;
import com.improving.userservice.service.impl.UserServiceImpl;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = UserController.class, useDefaultFilters = false)
@Import(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllUsers() throws Exception {
        List<UserDTO> userList = new ArrayList<>();
        userList.add(new UserDTO(1L, "Chris", "Macias",
                        LocalDate.of(1997, Month.APRIL, 19)));

        Mockito.when(userService.getAll()).thenReturn(userList);

        ResultActions response = mockMvc.perform(get("/user/"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].userId",is(1)))
                .andExpect(jsonPath("$.size()",is(userList.size())));
    }

    @Test
    void getUserFound() throws Exception {

        long userId = 1L;
        UserDTO user = new UserDTO(1, "Chris", "Macias",
                LocalDate.of(1997, Month.APRIL, 19));
        Mockito.when(userService.getById(userId)).thenReturn(Optional.of(user));

        ResultActions response = mockMvc.perform(get("/user/{id}", userId));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.userId",is(1)))
                .andExpect(jsonPath("$.firstName",is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName",is(user.getLastName())));
    }

    @Test
    void getUserNotFound() throws Exception {

        long userId = 1L;
        Mockito.when(userService.getById(userId)).thenReturn(Optional.empty());

        ResultActions response = mockMvc.perform(get("/user/{id}", userId));

        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void getUserBadRequest() throws Exception {

        ResultActions response = mockMvc.perform(get("/user/{id}", "userId"));

        response.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void saveUser() throws Exception {

        UserDTO userDTO = new UserDTO(1,"Chris", "Macias",
                LocalDate.of(1997, Month.APRIL, 19));
        Mockito.when(userService.save(any(UserCreationDTO.class))).thenReturn(userDTO);


        ResultActions response = mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)));

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId",is(1)))
                .andExpect(jsonPath("$.firstName",is(userDTO.getFirstName())))
                .andExpect(jsonPath("$.lastName",is(userDTO.getLastName())))
                .andExpect(jsonPath("$.dateOfBirth",is("19/04/1997")));
    }

    @Test
    void saveUserBadRequest() throws Exception {

      String userDTOBadParameters = "firstName";

        ResultActions response = mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTOBadParameters)));

        response.andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateUser() throws Exception {

        long userId = 1L;
        UserDTO userDTO = new UserDTO(1,"Christian", "Miguel",
                LocalDate.of(1997, Month.APRIL, 19));

        UserCreationDTO creationDTO = new UserCreationDTO("Christian", "Miguel",
                LocalDate.of(1997, Month.APRIL, 19));

        Mockito.when(userService.update(any(UserCreationDTO.class), any(long.class))).thenReturn(Optional.of(userDTO));

        ResultActions response = mockMvc.perform(put("/user/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(creationDTO)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId",is(1)))
                .andExpect(jsonPath("$.firstName",is(userDTO.getFirstName())))
                .andExpect(jsonPath("$.lastName",is(userDTO.getLastName())))
                .andExpect(jsonPath("$.dateOfBirth",is("19/04/1997")));
    }

    @Test
    void updateUserNotFound() throws Exception {

        long userId = 1L;

        UserCreationDTO creationDTO = new UserCreationDTO("Christian", "Miguel",
                LocalDate.of(1997, Month.APRIL, 19));


        Mockito.when(userService.update(creationDTO, userId)).thenReturn(Optional.empty());

        ResultActions response = mockMvc.perform(put("/user/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(creationDTO)));

        response.andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void updateUserBadRequest() throws Exception {

        long userId = 1L;

        String creationDTO = "BAD REQUEST BODY";

        ResultActions response = mockMvc.perform(put("/user/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(creationDTO)));

        response.andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteUser() throws Exception {

        long userId = 1L;

        Mockito.when(userService.delete(userId)).thenReturn(true);

        ResultActions response = mockMvc.perform(delete("/user/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON));

        response.andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteUserNotFound() throws Exception {

        long userId = 1L;

        Mockito.when(userService.delete(userId)).thenReturn(false);

        ResultActions response = mockMvc.perform(delete("/user/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON));

        response.andDo(print())
                .andExpect(status().isNotFound());
    }
}
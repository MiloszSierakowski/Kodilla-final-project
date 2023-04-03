package com.kodillafinalproject.controller;

import com.google.gson.Gson;
import com.kodillafinalproject.domain.User;
import com.kodillafinalproject.domain.UserDto;
import com.kodillafinalproject.mapper.UserMapper;
import com.kodillafinalproject.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @Test
    void findUsersByName() throws Exception {
        List<User> userList = List.of(
                new User(1L, "Alicja", "NVIDIA", "Olsztyn",
                        new HashSet<>(), new ArrayList<>(), new HashSet<>()),
                new User(2L, "Milosz", "NVIDIA", "Olsztyn",
                        new HashSet<>(), new ArrayList<>(), new HashSet<>())
        );
        List<UserDto> userDtoList = List.of(
                new UserDto(1L, "Alicja", "NVIDIA", "Olsztyn"),
                new UserDto(2L, "Milosz", "NVIDIA", "Olsztyn")
        );

        UserDto userDto = new UserDto(1L, "", "NVIDIA", "Olsztyn");


        when(userService.findByName(any(User.class))).thenReturn(userList);
        when(userMapper.mapToListUserDto(userList)).thenReturn(userDtoList);


        Gson gson = new Gson();

        String jsonContent = gson.toJson(userDto);

        mockMvc.
                perform(MockMvcRequestBuilders
                        .get("/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200)) //todo dopisać zmienne
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstname", Matchers.is("Alicja")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstname", Matchers.is("Milosz")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastname", Matchers.is("NVIDIA")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].lastname", Matchers.is("NVIDIA")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].city", Matchers.is("Olsztyn")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].city", Matchers.is("Olsztyn")));
    }
}
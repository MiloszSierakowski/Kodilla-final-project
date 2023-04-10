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
    void createUser() throws Exception {
        User user = new User(1L, "Milosz", "Sierak", "Grudziadz",
                new HashSet<>(), new ArrayList<>(), new HashSet<>());
        UserDto userDto = new UserDto(1L, "Milosz", "Sierak", "Grudziadz");

        when(userMapper.mapToUser(userDto)).thenReturn(user);
        when(userService.saveUser(any(User.class))).thenReturn(user);

        Gson gson = new Gson();

        String jsonContent = gson.toJson(userDto);

        mockMvc.
                perform(MockMvcRequestBuilders
                        .post("/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));

    }

    @Test
    void deleteUser() throws Exception {
        User user = new User(1L, "Test", "Test", "Test",
                new HashSet<>(), new ArrayList<>(), new HashSet<>());
        when(userService.findById(user.getId())).thenReturn(user);

        mockMvc.
                perform(MockMvcRequestBuilders
                        .delete("/v1/user/{id}", user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void deleteUserWithException() throws Exception {
        User user = new User(-1L, "Test", "Test", "Test",
                new HashSet<>(), new ArrayList<>(), new HashSet<>());

        when(userService.findById(user.getId())).thenThrow(new UserNotFoundException());

        mockMvc.
                perform(MockMvcRequestBuilders
                        .delete("/v1/user/{id}", user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    void updateUser() throws Exception {
        Long id = 1L;
        User user = new User(1L, "", "", "",
                new HashSet<>(), new ArrayList<>(), new HashSet<>());
        UserDto userDto = new UserDto(1L, "Test", "tests", "In Poland city");

        when(userService.findById(id)).thenReturn(user);
        when(userService.saveUser(any(User.class))).thenReturn(user);
        when(userMapper.mapToUserDto(any(User.class))).thenReturn(userDto);

        Gson gson = new Gson();

        String jsonContent = gson.toJson(userDto);

        mockMvc.
                perform(MockMvcRequestBuilders
                        .put("/v1/user/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname", Matchers.is("Test")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname", Matchers.is("tests")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city", Matchers.is("In Poland city")));
    }

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


        when(userService.findByName(userMapper.mapToUser(userDto))).thenReturn(userList);
        when(userMapper.mapToListUserDto(userList)).thenReturn(userDtoList);


        Gson gson = new Gson();

        String jsonContent = gson.toJson(userDto);

        mockMvc.
                perform(MockMvcRequestBuilders
                        .get("/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstname", Matchers.is("Alicja")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstname", Matchers.is("Milosz")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastname", Matchers.is("NVIDIA")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].lastname", Matchers.is("NVIDIA")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].city", Matchers.is("Olsztyn")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].city", Matchers.is("Olsztyn")));
    }

    @Test
    void findUsersByNameWithUserNotFoundException() throws Exception {
        UserDto userDto = new UserDto(1L, "", "", "Olsztyn");

        when(userService.findByName(userMapper.mapToUser(userDto))).thenThrow(new UserNotFoundException());

        Gson gson = new Gson();

        String jsonContent = gson.toJson(userDto);

        mockMvc.
                perform(MockMvcRequestBuilders
                        .get("/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    void findUsersByNameWithNoRequiredPersonDataException() throws Exception {
        UserDto userDto = new UserDto(1L, "", "", "Olsztyn");

        when(userService.findByName(userMapper.mapToUser(userDto))).thenThrow(new NoRequiredPersonDataException());

        Gson gson = new Gson();

        String jsonContent = gson.toJson(userDto);

        mockMvc.
                perform(MockMvcRequestBuilders
                        .get("/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void addFriend() throws Exception {
        Long userId = 1L;
        Long friendId = 2L;
        User user = new User(1L, "Milosz", "Sierak", "Grudziadz",
                new HashSet<>(), new ArrayList<>(), new HashSet<>());
        User friend = new User(2L, "Milosz", "Sierak", "Grudziadz",
                new HashSet<>(), new ArrayList<>(), new HashSet<>());

        when(userService.findById(userId)).thenReturn(user);
        when(userService.findById(friendId)).thenReturn(friend);

        mockMvc.
                perform(MockMvcRequestBuilders
                        .put("/v1/user/{userId}/friend/{friendId}", userId, friendId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void addFriendWithNoUserFoundException() throws Exception {
        Long userId = 1L;
        Long friendId = 2L;
        when(userService.findById(userId)).thenThrow(new UserNotFoundException());

        mockMvc.
                perform(MockMvcRequestBuilders
                        .put("/v1/user/{userId}/friend/{friendId}", userId, friendId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    void deleteFriend() throws Exception {
        Long userId = 1L;
        Long friendId = 2L;
        User user = new User(1L, "Milosz", "Sierak", "Grudziadz",
                new HashSet<>(), new ArrayList<>(), new HashSet<>());
        User friend = new User(2L, "Milosz", "Sierak", "Grudziadz",
                new HashSet<>(), new ArrayList<>(), new HashSet<>());

        when(userService.findById(userId)).thenReturn(user);
        when(userService.findById(friendId)).thenReturn(friend);

        mockMvc.
                perform(MockMvcRequestBuilders
                        .delete("/v1/user/{userId}/friend/{friendId}", userId, friendId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void deleteFriendWithNoUserFoundException() throws Exception {
        Long userId = 1L;
        Long friendId = 2L;

        when(userService.findById(userId)).thenThrow(new UserNotFoundException());

        mockMvc.
                perform(MockMvcRequestBuilders
                        .delete("/v1/user/{userId}/friend/{friendId}", userId, friendId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }
}
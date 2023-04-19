package com.kodillafinalproject.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kodillafinalproject.domain.User;
import com.kodillafinalproject.domain.UserDto;
import com.kodillafinalproject.mapper.LocalDateAdapter;
import com.kodillafinalproject.mapper.LocalTimeAdapter;
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

import java.time.LocalDate;
import java.time.LocalTime;
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
                new HashSet<>(), new ArrayList<>(), new ArrayList<>());
        UserDto userDto = new UserDto(1L, "Milosz", "Sierak", "Grudziadz",  new ArrayList<>(), new ArrayList<>());

        when(userMapper.mapToUser(userDto)).thenReturn(user);
        when(userService.saveUser(any(User.class))).thenReturn(user);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter()).create();

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
                new HashSet<>(), new ArrayList<>(), new ArrayList<>());
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
                new HashSet<>(), new ArrayList<>(), new ArrayList<>());

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
                new HashSet<>(), new ArrayList<>(), new ArrayList<>());
        UserDto userDto = new UserDto(1L, "Test", "tests", "In Poland city", new ArrayList<>(), new ArrayList<>());

        when(userService.findById(id)).thenReturn(user);
        when(userService.saveUser(any(User.class))).thenReturn(user);
        when(userMapper.mapToUserDto(any(User.class))).thenReturn(userDto);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter()).create();

        String jsonContent = gson.toJson(userDto);

        mockMvc.
                perform(MockMvcRequestBuilders
                        .put("/v1/user/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("Test")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.is("tests")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city", Matchers.is("In Poland city")));
    }

    @Test
    void findUsersByName() throws Exception {
        List<User> userList = List.of(
                new User(1L, "Alicja", "NVIDIA", "Olsztyn",
                        new HashSet<>(), new ArrayList<>(), new ArrayList<>()),
                new User(2L, "Milosz", "NVIDIA", "Olsztyn",
                        new HashSet<>(), new ArrayList<>(), new ArrayList<>())
        );
        List<UserDto> userDtoList = List.of(
                new UserDto(1L, "Alicja", "NVIDIA", "Olsztyn",  new ArrayList<>(), new ArrayList<>()),
                new UserDto(2L, "Milosz", "NVIDIA", "Olsztyn", new ArrayList<>(), new ArrayList<>())
        );

        UserDto userDto = new UserDto(1L, "", "NVIDIA", "Olsztyn",  new ArrayList<>(), new ArrayList<>());


        when(userService.findByName(userMapper.mapToUser(userDto))).thenReturn(userList);
        when(userMapper.mapToListUserDto(userList)).thenReturn(userDtoList);


        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter()).create();

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
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName", Matchers.is("Alicja")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName", Matchers.is("Milosz")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName", Matchers.is("NVIDIA")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].lastName", Matchers.is("NVIDIA")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].city", Matchers.is("Olsztyn")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].city", Matchers.is("Olsztyn")));
    }

    @Test
    void findUsersByNameWithUserNotFoundException() throws Exception {
        UserDto userDto = new UserDto(1L, "", "", "Olsztyn",  new ArrayList<>(), new ArrayList<>());

        when(userService.findByName(userMapper.mapToUser(userDto))).thenThrow(new UserNotFoundException());

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter()).create();

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
        UserDto userDto = new UserDto(1L, "", "", "Olsztyn",  new ArrayList<>(), new ArrayList<>());

        when(userService.findByName(userMapper.mapToUser(userDto))).thenThrow(new NoRequiredPersonDataException());

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter()).create();

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
                new HashSet<>(), new ArrayList<>(), new ArrayList<>());
        User friend = new User(2L, "Milosz", "Sierak", "Grudziadz",
                new HashSet<>(), new ArrayList<>(), new ArrayList<>());

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
                new HashSet<>(), new ArrayList<>(), new ArrayList<>());
        User friend = new User(2L, "Milosz", "Sierak", "Grudziadz",
                new HashSet<>(), new ArrayList<>(), new ArrayList<>());

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
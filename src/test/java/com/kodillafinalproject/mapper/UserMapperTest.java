package com.kodillafinalproject.mapper;

import com.kodillafinalproject.domain.EventDto;
import com.kodillafinalproject.domain.User;
import com.kodillafinalproject.domain.UserDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserMapperTest {
    private static User user;
    private static UserDto userDto;
    @Autowired
    private UserMapper userMapper;

    @BeforeAll
    static void setUp() {
        user = new User(1L, "Milosz", "Sier", "Gdansk",
                new HashSet<>(), new ArrayList<>(), new ArrayList<>());
        userDto = new UserDto(1L, "Milosz", "Sier", "Gdans", List.of(new UserDto()), List.of(new EventDto()));
    }

    @Test
    void mapToUser() {
        User mappedUser = userMapper.mapToUser(userDto);

        assertAll(
                () -> assertEquals(userDto.getId(), mappedUser.getId()),
                () -> assertEquals(userDto.getFirstName(), mappedUser.getFirstName()),
                () -> assertEquals(userDto.getLastName(), mappedUser.getLastName()),
                () -> assertEquals(userDto.getCity(), mappedUser.getCity())
        );
    }

    @Test
    void mapToUserWithNullField() {
        UserDto userDto1 = new UserDto(1L,"","Sierak","Olsztyn", List.of(new UserDto()), List.of(new EventDto()));
        User mappedUser = userMapper.mapToUser(userDto1);

        assertAll(
                () -> assertEquals(userDto1.getId(), mappedUser.getId()),
                () -> assertEquals(userDto1.getFirstName(), mappedUser.getFirstName()),
                () -> assertEquals(userDto1.getLastName(), mappedUser.getLastName()),
                () -> assertEquals(userDto1.getCity(), mappedUser.getCity())
        );
    }

    @Test
    void mapToUserDto() {
        UserDto mappedUserDto = userMapper.mapToUserDto(user);

        assertAll(
                () -> assertEquals(user.getId(), mappedUserDto.getId()),
                () -> assertEquals(user.getFirstName(), mappedUserDto.getFirstName()),
                () -> assertEquals(user.getLastName(), mappedUserDto.getLastName()),
                () -> assertEquals(user.getCity(), mappedUserDto.getCity())
        );
    }

    @Test
    void mapToListUser() {
        List<User> userList = new ArrayList<>();
        userList.add(user);
        userList.add(new User(2L, "Ala", "Ma kota", "Warszawa",
                new HashSet<>(), new ArrayList<>(), new ArrayList<>()));
        userList.add(new User(3L, "Bartosz", "Ma problem", "Krakow",
                new HashSet<>(), new ArrayList<>(), new ArrayList<>()));

        List<UserDto> mappedUserDtoList = userMapper.mapToListUserDto(userList);

        assertAll(
                () -> assertEquals(userList.size(), mappedUserDtoList.size()),
                () -> assertEquals(userList.get(1).getFirstName(), mappedUserDtoList.get(1).getFirstName()),
                () -> assertEquals(userList.get(2).getLastName(), mappedUserDtoList.get(2).getLastName())
        );
    }

    @Test
    void mapToListUserDto() {
        List<UserDto> userDtoList = new ArrayList<>();
        userDtoList.add(userDto);
        userDtoList.add(new UserDto(2L, "Ala", "Ma kota", "Warszawa", List.of(new UserDto()), List.of(new EventDto())));
        userDtoList.add(new UserDto(3L, "Bartosz", "Ma problem", "Krakow", List.of(new UserDto()), List.of(new EventDto())));

        List<User> mappedUserList = userMapper.mapToListUser(userDtoList);

        assertAll(
                () -> assertEquals(userDtoList.size(),                  mappedUserList.size()),
                () -> assertEquals(userDtoList.get(1).getFirstName(),   mappedUserList.get(1).getFirstName()),
                () -> assertEquals(userDtoList.get(2).getLastName(),    mappedUserList.get(2).getLastName())
        );
    }
}
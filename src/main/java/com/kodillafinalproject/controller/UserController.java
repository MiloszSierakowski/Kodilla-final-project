package com.kodillafinalproject.controller;

import com.kodillafinalproject.domain.User;
import com.kodillafinalproject.domain.UserDto;
import com.kodillafinalproject.mapper.UserMapper;
import com.kodillafinalproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createUser(@RequestBody UserDto userDto) {
        User user = userMapper.mapToUser(userDto);
        userService.saveUser(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) throws UserNotFoundException {
        User user = userService.findById(id);
        userService.deleteUserById(user.getId());
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) throws UserNotFoundException {
        User user = userService.findById(id);
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setCity(userDto.getCity());
        User savedUser = userService.saveUser(user);
        return ResponseEntity.ok(userMapper.mapToUserDto(savedUser));
    }

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDto>> findUsersByName(@RequestBody UserDto userDto) throws NoRequiredPersonDataException, UserNotFoundException {
        List<User> userList = userService.findByName(userMapper.mapToUser(userDto));
        return ResponseEntity.ok(userMapper.mapToListUserDto(userList));
    }

    @PutMapping(value = "/{userId}/friend/{friendId}")
    public ResponseEntity<Void> addFriend(@PathVariable Long userId, @PathVariable Long friendId) throws UserNotFoundException {
        User user = userService.findById(userId);
        User friend = userService.findById(friendId);
        user.getFriendList().add(friend);
        userService.saveUser(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{userId}/friend/{friendId}")
    public ResponseEntity<Void> deleteFriend(@PathVariable Long userId, @PathVariable Long friendId) throws UserNotFoundException {
        User user = userService.findById(userId);
        User friend = userService.findById(friendId);
        user.getFriendList().remove(friend);
        userService.saveUser(user);
        return ResponseEntity.ok().build();
    }
}

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

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDto>> findUsersByName(@RequestBody UserDto userDto) throws Exception {
        //todo ogarnać exeptiony
        List<User> userList = userService.findByName(userMapper.mapToUser(userDto));
        return ResponseEntity.ok(userMapper.mapToListUserDto(userList));
    }
}

package com.kodillafinalproject.controller;


import com.kodillafinalproject.domain.Event;
import com.kodillafinalproject.domain.EventDto;
import com.kodillafinalproject.domain.User;
import com.kodillafinalproject.domain.UserDto;
import com.kodillafinalproject.mapper.EventMapper;
import com.kodillafinalproject.mapper.UserMapper;
import com.kodillafinalproject.service.EventService;
import com.kodillafinalproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/v1/userInEv")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserInterestedEventsController {

    private final UserService userService;
    private final EventService eventService;
    private final UserMapper userMapper;
    private final EventMapper eventMapper;

    @PutMapping(value = "event/{eventId}/user/{userId}")
    public ResponseEntity<Void> addEventToUser(@PathVariable Long eventId, @PathVariable Long userId) throws UserNotFoundException, EventNotFoundException {
        Event event = eventService.findById(eventId);
        User user = userService.findById(userId);
        user.getEvents().add(event);
        userService.saveUser(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "event/{eventId}/user/{userId}")
    public ResponseEntity<Void> removeUserFromEvent(@PathVariable Long eventId, @PathVariable Long userId) throws UserNotFoundException, EventNotFoundException {
        Event event = eventService.findById(eventId);
        User user = userService.findById(userId);
        event.getUsers().remove(user);
        user.getEvents().remove(event);
        eventService.saveEvent(event);
        userService.saveUser(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/users/{eventId}")
    public ResponseEntity<List<UserDto>> showAllUsersAssignedToEvent(@PathVariable Long eventId) throws EventNotFoundException {
        Event event = eventService.findById(eventId);
        List<User> users = event.getUsers();
        return ResponseEntity.ok(userMapper.mapToListUserDto(users));
    }

    @GetMapping(value = "/events/{userId}")
    public ResponseEntity<List<EventDto>> getTheListOfEventsAssignedToTheUser(@PathVariable Long userId) throws UserNotFoundException {
        User user = userService.findById(userId);
        List<Event> events = user.getEvents();
        return ResponseEntity.ok(eventMapper.mapToListsOfEventsDto(events));
    }

    @GetMapping(value = "/friends-events/{userId}")
    public ResponseEntity<Set<UserDto>> getTheListOfEventsAssignedToTheUserFriends(@PathVariable Long userId) throws UserNotFoundException {
        User user = userService.findById(userId);
        Set<User> friends = user.getFriendList();
        return ResponseEntity.ok(userMapper.mapToListUserDtoWithEventList(friends));
    }
}

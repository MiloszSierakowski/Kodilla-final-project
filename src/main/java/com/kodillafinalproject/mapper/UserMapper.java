package com.kodillafinalproject.mapper;

import com.kodillafinalproject.domain.User;
import com.kodillafinalproject.domain.UserDto;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = EventMapper.class)
public interface UserMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "city", target = "city")
    @Mapping(ignore = true, target = "friendList")
    @Mapping(ignore = true, target = "noteList")
    @Mapping(ignore = true, target = "events")
    User mapToUser(UserDto userDto);

    @Named("mapToUserDto")
    @InheritInverseConfiguration(name = "mapToUser")
    @Mapping(ignore = true, target = "eventDtoList")
    @Mapping(ignore = true, target = "userDtoList")
    UserDto mapToUserDto(User user);

    @Named("mapToUserDtoWithEventList")
    @InheritInverseConfiguration(name = "mapToUser")
    @Mapping(source = "events", target = "eventDtoList")
    UserDto mapToUserDtoWithEventList(User user);

    List<User> mapToListUser(List<UserDto> userDtoList);

    @IterableMapping(qualifiedByName = "mapToUserDto")
    List<UserDto> mapToListUserDto(List<User> userList);

    @IterableMapping(qualifiedByName = "mapToUserDtoWithEventList")
    Set<UserDto> mapToListUserDtoWithEventList(Set<User> userList);
}

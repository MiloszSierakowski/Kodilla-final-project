package com.kodillafinalproject.mapper;

import com.kodillafinalproject.domain.User;
import com.kodillafinalproject.domain.UserDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "city", target = "city")
    @Mapping(ignore = true, target = "friendList")
    @Mapping(ignore = true, target = "noteList")
    @Mapping(ignore = true, target = "events")
    User mapToUser(UserDto userDto);

    @InheritInverseConfiguration(name = "mapToUser")
    UserDto mapToUserDto(User user);

    List<User> mapToListUser(List<UserDto> userDtoList);

    List<UserDto> mapToListUserDto(List<User> userList);

}

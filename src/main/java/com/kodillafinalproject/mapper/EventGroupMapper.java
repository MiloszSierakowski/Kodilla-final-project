package com.kodillafinalproject.mapper;

import com.kodillafinalproject.domain.EventGroup;
import com.kodillafinalproject.domain.EventGroupDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventGroupMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(ignore = true, target = "eventList")
    EventGroup mapToEventGroup(EventGroupDto eventGroupDto);

    @InheritInverseConfiguration(name = "mapToEventGroup")
    EventGroupDto mapToEventGroupDto(EventGroup eventGroup);

}

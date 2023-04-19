package com.kodillafinalproject.mapper;

import com.kodillafinalproject.domain.Event;
import com.kodillafinalproject.domain.EventDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "nameOfEvent", target = "nameOfEvent")
    @Mapping(source = "location", target = "location")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "eventTime", target = "eventTime")
    @Mapping(source = "eventDate", target = "eventDate")
    @Mapping(ignore = true, target = "eventGroup")
    @Mapping(ignore = true, target = "users")
    Event mapToEvent(EventDto eventDto);

    @InheritInverseConfiguration(name = "mapToEvent")
    EventDto mapToEventDto(Event event);
    List<Event> mapToListsOfEvents(List<EventDto> eventDtos);
    List<EventDto> mapToListsOfEventsDto(List<Event> events);

}

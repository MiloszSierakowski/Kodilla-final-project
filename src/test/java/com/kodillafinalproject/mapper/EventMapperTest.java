package com.kodillafinalproject.mapper;

import com.kodillafinalproject.domain.Event;
import com.kodillafinalproject.domain.EventDto;
import com.kodillafinalproject.domain.EventGroup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class EventMapperTest {
    @Autowired
    private EventMapper eventMapper;
    private static Event event;
    private static EventDto eventDto;

    @BeforeAll
    static void setUp() {
        event = new Event(1L, "Test", "Intellij",
                "Testowanie mappera", LocalTime.now(), LocalDate.now(),
                new EventGroup(), new ArrayList<>());
        eventDto = new EventDto(1L, "Test", "Intellij",
                "Testowanie mappera", LocalTime.now(), LocalDate.now(),
                2L);
    }

    @Test
    void mapToEvent() {
        Event mappedEvent = eventMapper.mapToEvent(eventDto);

        assertAll(
                () -> assertEquals(eventDto.getId(), mappedEvent.getId()),
                () -> assertEquals(eventDto.getNameOfEvent(), mappedEvent.getNameOfEvent()),
                () -> assertEquals(eventDto.getLocation(), mappedEvent.getLocation()),
                () -> assertEquals(eventDto.getDescription(), mappedEvent.getDescription()),
                () -> assertEquals(eventDto.getEventTime(), mappedEvent.getEventTime()),
                () -> assertEquals(eventDto.getEventDate(), mappedEvent.getEventDate())
        );
    }

    @Test
    void mapToEventDto() {
        EventDto mappedEventDto = eventMapper.mapToEventDto(event);

        assertAll(
                () -> assertEquals(event.getId(),            mappedEventDto.getId()),
                () -> assertEquals(event.getNameOfEvent(),   mappedEventDto.getNameOfEvent()),
                () -> assertEquals(event.getLocation(),      mappedEventDto.getLocation()),
                () -> assertEquals(event.getDescription(),   mappedEventDto.getDescription()),
                () -> assertEquals(event.getEventTime(),     mappedEventDto.getEventTime()),
                () -> assertEquals(event.getEventDate(),     mappedEventDto.getEventDate())
        );
    }
}
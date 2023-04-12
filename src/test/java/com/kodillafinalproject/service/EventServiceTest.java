package com.kodillafinalproject.service;

import com.kodillafinalproject.controller.EventNotFoundException;
import com.kodillafinalproject.domain.Event;
import com.kodillafinalproject.domain.EventGroup;
import com.kodillafinalproject.repository.EventGroupRepository;
import com.kodillafinalproject.repository.EventRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EventServiceTest {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventService eventService;

    @Autowired
    private EventGroupRepository eventGroupRepository;
    private EventGroup eventGroup;

    private Event event;

    @BeforeEach
    void setUp() {
        eventGroup = eventGroupRepository.save(new EventGroup(-1L, "Testowy", new ArrayList<>()));
        event = Event.builder()
                .id(-1L)
                .nameOfEvent("Test")
                .description("Testowy event")
                .location("JUnit")
                .eventDate(LocalDate.now())
                .eventTime(LocalTime.of(15, 30))
                .eventGroup(eventGroup)
                .build();
    }

    @AfterEach
    void cleanUp() {
        eventGroupRepository.deleteById(eventGroup.getId());
    }


    @Test
    void saveEvent() {
        Event savedEvent = eventService.saveEvent(event);
        Optional<Event> searchedEvent = eventRepository.findById(savedEvent.getId());

        try {
            assertTrue(searchedEvent.isPresent());
            assertEquals(event.getNameOfEvent(), searchedEvent.get().getNameOfEvent());
            assertEquals(event.getDescription(), searchedEvent.get().getDescription());
            assertEquals(event.getLocation(), searchedEvent.get().getLocation());
            assertEquals(event.getEventDate(), searchedEvent.get().getEventDate());
            assertEquals(event.getEventTime(), searchedEvent.get().getEventTime());
            assertEquals(event.getEventGroup().getId(), searchedEvent.get().getEventGroup().getId());
        } finally {
            eventRepository.deleteById(savedEvent.getId());
        }
    }

    @Test
    void findById() throws EventNotFoundException {
        Event savedEvent = eventRepository.save(event);
        Event searchedEvent = eventService.findById(savedEvent.getId());

        try {
            assertEquals(event.getNameOfEvent(), searchedEvent.getNameOfEvent());
            assertEquals(event.getDescription(), searchedEvent.getDescription());
            assertEquals(event.getLocation(), searchedEvent.getLocation());
            assertEquals(event.getEventDate(), searchedEvent.getEventDate());
            assertEquals(event.getEventTime(), searchedEvent.getEventTime());
            assertEquals(event.getEventGroup().getId(), searchedEvent.getEventGroup().getId());
        } finally {
            eventRepository.deleteById(savedEvent.getId());
        }
    }

    @Test
    void findByIdThrowEventNotFoundException(){
        assertThrows(EventNotFoundException.class, () -> eventService.findById(-1L));
    }

    @Test
    void exists() {
        Event savedEvent = eventRepository.save(event);
        try {
            assertTrue(eventService.exists(savedEvent.getId()));
        } finally {
            eventRepository.deleteById(savedEvent.getId());
        }

    }

    @Test
    void deleteEvent() {
        Event savedEvent = eventRepository.save(event);
        eventService.deleteEvent(savedEvent);
        Optional<Event> searchedNote = eventRepository.findById(savedEvent.getId());

        assertFalse(searchedNote.isPresent());
        assertTrue(eventGroupRepository.existsById(eventGroup.getId()));

    }
}
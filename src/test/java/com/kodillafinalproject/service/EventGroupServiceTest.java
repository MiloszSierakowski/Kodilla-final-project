package com.kodillafinalproject.service;

import com.kodillafinalproject.controller.EventGroupNotFoundException;
import com.kodillafinalproject.domain.EventGroup;
import com.kodillafinalproject.repository.EventGroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EventGroupServiceTest {

    @Autowired
    private EventGroupService eventGroupService;
    @Autowired
    private EventGroupRepository eventGroupRepository;
    private EventGroup eventGroup;

    @BeforeEach
    void setUp() {
        eventGroup = new EventGroup(-1L, "Testowy", new ArrayList<>());
    }

    @Test
    void saveEventGroup() {
        EventGroup savedEventGroup = eventGroupService.saveEventGroup(eventGroup);
        Optional<EventGroup> searchedEvent = eventGroupRepository.findById(savedEventGroup.getId());

        try {
            assertTrue(searchedEvent.isPresent());
            assertEquals(savedEventGroup.getId(), searchedEvent.get().getId());
            assertEquals(savedEventGroup.getName(), searchedEvent.get().getName());
        } finally {
            eventGroupRepository.deleteById(savedEventGroup.getId());
        }
    }

    @Test
    void findById() throws EventGroupNotFoundException {
        EventGroup savedEventGroup = eventGroupRepository.save(eventGroup);
        EventGroup searchedEvent = eventGroupService.findById(savedEventGroup.getId());

        try {
            assertEquals(savedEventGroup.getId(), searchedEvent.getId());
            assertEquals(savedEventGroup.getName(), searchedEvent.getName());
        } finally {
            eventGroupRepository.deleteById(savedEventGroup.getId());
        }
    }

    @Test
    void findByIdThrowEventGroupNotFoundException() {
        assertThrows(EventGroupNotFoundException.class, () -> eventGroupService.findById(-1L));
    }

    @Test
    void exists() {
        EventGroup savedEventGroup = eventGroupRepository.save(eventGroup);
        try {
            assertTrue(eventGroupService.exists(savedEventGroup.getId()));
        } finally {
            eventGroupRepository.deleteById(savedEventGroup.getId());
        }
    }

    @Test
    void deleteById() {
        EventGroup savedEventGroup = eventGroupRepository.save(eventGroup);
        eventGroupService.deleteById(savedEventGroup.getId());
        Optional<EventGroup> searchedNote = eventGroupRepository.findById(savedEventGroup.getId());

        assertFalse(searchedNote.isPresent());

    }
}
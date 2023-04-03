package com.kodillafinalproject.mapper;

import com.kodillafinalproject.domain.EventGroup;
import com.kodillafinalproject.domain.EventGroupDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EventGroupMapperTest {
    @Autowired
    private EventGroupMapper eventGroupMapper;
    private static EventGroup eventGroup;
    private static EventGroupDto eventGroupDto;

    @BeforeAll
    static void setUp() {
        eventGroup = new EventGroup(1L, "Outside", new ArrayList<>());
        eventGroupDto = new EventGroupDto(1L, "Outside");
    }

    @Test
    void mapToEventGroup() {

        EventGroup mappedEventGroup = eventGroupMapper.mapToEventGroup(eventGroupDto);

        assertAll(
                () -> assertEquals(eventGroupDto.getId(), mappedEventGroup.getId()),
                () -> assertEquals(eventGroupDto.getName(), mappedEventGroup.getName())
        );

    }

    @Test
    void mapToEventGroupDto() {
        EventGroupDto mappedEventGroupDto = eventGroupMapper.mapToEventGroupDto(eventGroup);

        assertAll(
                () -> assertEquals(eventGroupDto.getId(), mappedEventGroupDto.getId()),
                () -> assertEquals(eventGroupDto.getName(), mappedEventGroupDto.getName())
        );
    }
}
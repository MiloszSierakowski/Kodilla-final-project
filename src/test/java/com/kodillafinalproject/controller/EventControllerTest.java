package com.kodillafinalproject.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kodillafinalproject.domain.Event;
import com.kodillafinalproject.domain.EventDto;
import com.kodillafinalproject.domain.EventGroup;
import com.kodillafinalproject.mapper.EventMapper;
import com.kodillafinalproject.mapper.LocalDateAdapter;
import com.kodillafinalproject.mapper.LocalTimeAdapter;
import com.kodillafinalproject.service.EventService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(EventController.class)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @MockBean
    private EventMapper eventMapper;

    @Test
    void createEvent() throws Exception {
        Event event = Event.builder()
                .id(0L)
                .nameOfEvent("Test")
                .description("Testowy event")
                .location("JUnit")
                .eventDate(LocalDate.now())
                .eventTime(LocalTime.of(15, 30))
                .eventGroup(new EventGroup())
                .build();

        EventDto eventDto = EventDto.builder()
                .id(0L)
                .nameOfEvent("Testowy")
                .description("Testowy event")
                .location("JUnit")
                .eventDate(LocalDate.now())
                .eventTime(LocalTime.of(15, 30))
                .eventGroupId(1L)
                .build();

        when(eventMapper.mapToEvent(any(EventDto.class))).thenReturn(event);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter()).create();

        String jsonContent = gson.toJson(eventDto);

        mockMvc.
                perform(MockMvcRequestBuilders
                        .post("/v1/event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void getEvent() throws Exception {

        Event event = Event.builder()
                .id(0L)
                .nameOfEvent("Test")
                .description("Testowy event")
                .location("JUnit")
                .eventDate(LocalDate.now())
                .eventTime(LocalTime.of(15, 30))
                .eventGroup(new EventGroup())
                .build();

        EventDto eventDto = EventDto.builder()
                .id(0L)
                .nameOfEvent("Test")
                .description("Testowy event")
                .location("JUnit")
                .eventDate(LocalDate.now())
                .eventTime(LocalTime.of(15, 30))
                .eventGroupId(0L)
                .build();

        when(eventService.findById(anyLong())).thenReturn(event);
        when(eventMapper.mapToEventDto(any(Event.class))).thenReturn(eventDto);

        mockMvc.
                perform(MockMvcRequestBuilders
                        .get("/v1/event/{eventId}", event.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nameOfEvent", Matchers.is("Test")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is("Testowy event")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.location", Matchers.is("JUnit")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.eventDate", Matchers.is(eventDto.getEventDate().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.eventTime", Matchers.is(containsString("15:30"))));
    }

    @Test
    void getEventThrow() throws Exception {
        when(eventService.findById(anyLong())).thenThrow(new EventNotFoundException());

        mockMvc.
                perform(MockMvcRequestBuilders
                        .get("/v1/event/{eventId}", -1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    void updateEvent() throws Exception {
        Event event = Event.builder()
                .id(0L)
                .nameOfEvent("Testowy")
                .description("Testowy event")
                .location("JUnit")
                .eventDate(LocalDate.now())
                .eventTime(LocalTime.of(15, 30))
                .eventGroup(new EventGroup())
                .build();

        EventDto eventDto = EventDto.builder()
                .id(0L)
                .nameOfEvent("Testowy")
                .description("Testowy event")
                .location("JUnit")
                .eventDate(LocalDate.now())
                .eventTime(LocalTime.of(15, 30))
                .eventGroupId(1L)
                .build();

        when(eventMapper.mapToEvent(any(EventDto.class))).thenReturn(event);
        when(eventService.saveEvent(any(Event.class))).thenReturn(event);
        when(eventMapper.mapToEventDto(any(Event.class))).thenReturn(eventDto);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter()).create();

        String jsonContent = gson.toJson(eventDto);

        mockMvc.
                perform(MockMvcRequestBuilders
                        .put("/v1/event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nameOfEvent", Matchers.is("Testowy")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is("Testowy event")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.location", Matchers.is("JUnit")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.eventDate", Matchers.is(eventDto.getEventDate().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.eventTime", Matchers.is(containsString("15:30"))));
    }

    @Test
    void deleteEvent() throws Exception {

        Event event = Event.builder()
                .id(0L)
                .nameOfEvent("Test")
                .description("Testowy event")
                .location("JUnit")
                .eventDate(LocalDate.now())
                .eventTime(LocalTime.of(15, 30))
                .eventGroup(new EventGroup())
                .build();

        EventDto eventDto = EventDto.builder()
                .id(0L)
                .nameOfEvent("Testowy")
                .description("Testowy event")
                .location("JUnit")
                .eventDate(LocalDate.now())
                .eventTime(LocalTime.of(15, 30))
                .eventGroupId(1L)
                .build();

        when(eventService.findById(anyLong())).thenReturn(event);

        mockMvc.
                perform(MockMvcRequestBuilders
                        .delete("/v1/event/{eventId}", eventDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }
}
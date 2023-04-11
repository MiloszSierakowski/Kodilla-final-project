package com.kodillafinalproject.controller;

import com.google.gson.Gson;
import com.kodillafinalproject.domain.Event;
import com.kodillafinalproject.domain.EventGroup;
import com.kodillafinalproject.domain.EventGroupDto;
import com.kodillafinalproject.mapper.EventGroupMapper;
import com.kodillafinalproject.service.EventGroupService;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(EventGroupController.class)
class EventGroupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventGroupService groupService;

    @MockBean
    private EventGroupMapper eventGroupMapper;

    @Test
    void createGroupEvent() throws Exception {
        EventGroup eventGroup = new EventGroup(0L, "Testowa", new ArrayList<>());
        EventGroupDto eventGroupDto = new EventGroupDto(0L, "Testowa");

        when(eventGroupMapper.mapToEventGroup(eventGroupDto)).thenReturn(eventGroup);
        when(groupService.saveEventGroup(any(EventGroup.class))).thenReturn(eventGroup);

        Gson gson = new Gson();

        String jsonContent = gson.toJson(eventGroupDto);

        mockMvc.
                perform(MockMvcRequestBuilders
                        .post("/v1/event-group")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void getEventGroupEvent() throws Exception {
        EventGroup eventGroup = new EventGroup(0L, "Testowa", new ArrayList<>());
        EventGroupDto eventGroupDto = new EventGroupDto(0L, "Testowa");

        when(groupService.findById(anyLong())).thenReturn(eventGroup);
        when(eventGroupMapper.mapToEventGroupDto(eventGroup)).thenReturn(eventGroupDto);

        mockMvc.
                perform(MockMvcRequestBuilders
                        .get("/v1/event-group/{groupId}", eventGroupDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Testowa")));
    }

    @Test
    void getEventGroupEventThrowEventGroupNotFoundException() throws Exception {
        EventGroupDto eventGroupDto = new EventGroupDto(0L, "Testowa");

        when(groupService.findById(anyLong())).thenThrow(new EventGroupNotFoundException());

        mockMvc.
                perform(MockMvcRequestBuilders
                        .get("/v1/event-group/{groupId}", eventGroupDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    void updateGroupEvent() throws Exception {
        EventGroup eventGroup = new EventGroup(0L, "", new ArrayList<>());
        EventGroupDto eventGroupDto = new EventGroupDto(0L, "Testowa");

        when(groupService.findById(anyLong())).thenReturn(eventGroup);
        when(groupService.saveEventGroup(eventGroup)).thenReturn(eventGroup);
        when(eventGroupMapper.mapToEventGroupDto(eventGroup)).thenReturn(eventGroupDto);

        Gson gson = new Gson();

        String jsonContent = gson.toJson(eventGroupDto);

        mockMvc.
                perform(MockMvcRequestBuilders
                        .get("/v1/event-group/{groupId}", eventGroupDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Testowa")));
    }

    @Test
    void updateGroupEventThrowEventGroupNotFoundException() throws Exception {
        EventGroupDto eventGroupDto = new EventGroupDto(0L, "Testowa");

        when(groupService.findById(anyLong())).thenThrow(new EventGroupNotFoundException());

        mockMvc.
                perform(MockMvcRequestBuilders
                        .get("/v1/event-group/{groupId}", eventGroupDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    void deleteGroupEvent() throws Exception {
        Long eventGroupId = 2L;
        EventGroup eventGroup = new EventGroup(0L, "Test", new ArrayList<>());

        when(groupService.exists(anyLong())).thenReturn(true);
        when(groupService.findById(anyLong())).thenReturn(eventGroup);

        mockMvc.
                perform(MockMvcRequestBuilders
                        .delete("/v1/event-group/{eventGroupId}", eventGroupId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void deleteGroupEventWithThrowNotEmptyEventGroupException() throws Exception {
        Long eventGroupId = 2L;
        EventGroup eventGroup = new EventGroup(0L, "Test", List.of(
                new Event(),
                new Event()
        ));

        when(groupService.exists(anyLong())).thenReturn(true);
        when(groupService.findById(anyLong())).thenReturn(eventGroup);

        mockMvc.
                perform(MockMvcRequestBuilders
                        .delete("/v1/event-group/{eventGroupId}", eventGroupId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().is(409));
    }
}
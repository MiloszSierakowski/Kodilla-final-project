package com.kodillafinalproject.controller;

import com.kodillafinalproject.domain.Event;
import com.kodillafinalproject.domain.EventDto;
import com.kodillafinalproject.mapper.EventMapper;
import com.kodillafinalproject.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/event")
@RequiredArgsConstructor
@CrossOrigin("*")
public class EventController {

    private final EventService eventService;

    private final EventMapper eventMapper;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createEvent(@RequestBody EventDto eventDto) {
        Event event = eventMapper.mapToEvent(eventDto);
        eventService.saveEvent(event);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{eventId}")
    public ResponseEntity<EventDto> getEvent(@PathVariable Long eventId) throws EventNotFoundException {
        Event event = eventService.findById(eventId);
        return ResponseEntity.ok(eventMapper.mapToEventDto(event));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventDto> updateEvent(@RequestBody EventDto eventDto) {
        Event event = eventMapper.mapToEvent(eventDto);
        System.out.println(event.toString());
        Event updatedEvent = eventService.saveEvent(event);
        return ResponseEntity.ok(eventMapper.mapToEventDto(updatedEvent));
    }

    @DeleteMapping(value = "/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long eventId) throws EventNotFoundException {
        Event event = eventService.findById(eventId);
        eventService.deleteEvent(event);
        return ResponseEntity.ok().build();
    }

    //todo przerobić pozostałe endpointy jak ten put i delete
}

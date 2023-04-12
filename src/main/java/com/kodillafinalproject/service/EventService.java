package com.kodillafinalproject.service;

import com.kodillafinalproject.controller.EventNotFoundException;
import com.kodillafinalproject.domain.Event;
import com.kodillafinalproject.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    public Event saveEvent(Event event){
        return eventRepository.save(event);
    }

    public Event findById(Long eventId) throws EventNotFoundException {
        return eventRepository.findById(eventId).orElseThrow(EventNotFoundException::new);
    }

    public boolean exists(Long eventId) {
        return eventRepository.existsById(eventId);
    }

    public void deleteEvent(Event event) {
        eventRepository.delete(event);
    }
}

package com.kodillafinalproject.service;

import com.kodillafinalproject.controller.EventGroupNotFoundException;
import com.kodillafinalproject.domain.EventGroup;
import com.kodillafinalproject.repository.EventGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventGroupService {
    private final EventGroupRepository eventGroupRepository;

    public EventGroup saveEventGroup(EventGroup eventGroup) {
        return eventGroupRepository.save(eventGroup);
    }

    public EventGroup findById(Long groupId) throws EventGroupNotFoundException {
        return eventGroupRepository.findById(groupId).orElseThrow(EventGroupNotFoundException::new);
    }

    public boolean exists(Long eventGroupId) {
        return eventGroupRepository.existsById(eventGroupId);
    }

    public void deleteById(Long eventGroupId) {
        eventGroupRepository.deleteById(eventGroupId);
    }

}

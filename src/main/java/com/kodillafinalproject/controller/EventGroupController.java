package com.kodillafinalproject.controller;

import com.kodillafinalproject.domain.EventGroup;
import com.kodillafinalproject.domain.EventGroupDto;
import com.kodillafinalproject.mapper.EventGroupMapper;
import com.kodillafinalproject.service.EventGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/event-group")
@RequiredArgsConstructor
@CrossOrigin("*")
public class EventGroupController {

    private final EventGroupMapper groupMapper;

    private final EventGroupService eventGroupService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createGroupEvent(@RequestBody EventGroupDto eventGroupDto) {
        EventGroup eventGroup = groupMapper.mapToEventGroup(eventGroupDto);
        eventGroupService.saveEventGroup(eventGroup);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{eventGroupId}")
    public ResponseEntity<EventGroupDto> getEventGroupEvent(@PathVariable Long eventGroupId) throws EventGroupNotFoundException {
        EventGroup eventGroup = eventGroupService.findById(eventGroupId);
        return ResponseEntity.ok(groupMapper.mapToEventGroupDto(eventGroup));
    }

    @PutMapping(value = "/{eventGroupId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventGroupDto> updateGroupEvent(@PathVariable Long eventGroupId, @RequestBody EventGroupDto eventGroupDto) throws EventGroupNotFoundException {
        EventGroup eventGroup = eventGroupService.findById(eventGroupId);
        eventGroup.setName(eventGroupDto.getName());
        EventGroup updatedEventGroup = eventGroupService.saveEventGroup(eventGroup);
        return ResponseEntity.ok(groupMapper.mapToEventGroupDto(updatedEventGroup));
    }

    @DeleteMapping(value = "/{eventGroupId}")
    public ResponseEntity<Void> deleteGroupEvent(@PathVariable Long eventGroupId) throws EventGroupNotFoundException, NotEmptyEventGroupDeletionException {
        if (eventGroupService.exists(eventGroupId)) {
            EventGroup eventGroup = eventGroupService.findById(eventGroupId);
            if (eventGroup.getEventList().isEmpty()) {
                eventGroupService.deleteById(eventGroupId);
            } else {
                throw new NotEmptyEventGroupDeletionException();
            }
        }
        return ResponseEntity.ok().build();
    }

}

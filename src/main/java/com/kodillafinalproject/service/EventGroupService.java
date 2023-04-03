package com.kodillafinalproject.service;

import com.kodillafinalproject.repository.EventGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventGroupService {
    private final EventGroupRepository eventGroupRepository;
}

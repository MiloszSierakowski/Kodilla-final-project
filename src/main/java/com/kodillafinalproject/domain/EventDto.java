package com.kodillafinalproject.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {
    private Long id;
    private String nameOfEvent;
    private String location;
    private String description;
    private LocalTime eventTime;
    private LocalDate eventDate;
    private Long eventGroupId;
}

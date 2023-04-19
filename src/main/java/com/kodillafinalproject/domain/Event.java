package com.kodillafinalproject.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "EVENT")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "EVENT_ID")
    private Long id;
    @Column(name = "NAME_OF_EVENT")
    private String nameOfEvent;
    @Column(name = "LOCATION")
    private String location;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "EVENT_TIME")
    private LocalTime eventTime;
    @Column(name = "EVENT_DATE")
    private LocalDate eventDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EVENT_GROUP_ID")
    private EventGroup eventGroup;

    @ManyToMany(mappedBy = "events", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();
}

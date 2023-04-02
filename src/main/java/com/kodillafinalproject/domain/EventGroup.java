package com.kodillafinalproject.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "EVENTGROUP")
public class EventGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "EVENT_GROUP_ID")
    private Long id;
    @Column(name = "Name")
    private String name;
    @OneToMany(
            targetEntity = Event.class,
            mappedBy = "eventGroup",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Event> eventList = new ArrayList<>();
}

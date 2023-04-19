package com.kodillafinalproject.repository;

import com.kodillafinalproject.domain.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface EventRepository extends CrudRepository<Event, Long> {
}

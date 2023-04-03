package com.kodillafinalproject.repository;

import com.kodillafinalproject.domain.Event;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface EventRepository extends CrudRepository<Event, Long> {
}

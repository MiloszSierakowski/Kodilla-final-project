package com.kodillafinalproject.repository;

import com.kodillafinalproject.domain.EventGroup;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface EventGroupRepository extends CrudRepository<EventGroup,Long> {
}

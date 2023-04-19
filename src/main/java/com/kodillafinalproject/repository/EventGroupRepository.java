package com.kodillafinalproject.repository;

import com.kodillafinalproject.domain.EventGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface EventGroupRepository extends CrudRepository<EventGroup,Long> {
}

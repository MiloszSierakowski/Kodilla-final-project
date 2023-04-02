package com.kodillafinalproject.repository;

import com.kodillafinalproject.domain.Note;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface NoteRepository extends CrudRepository<Note, Long> {

}

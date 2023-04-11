package com.kodillafinalproject.repository;

import com.kodillafinalproject.domain.Note;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface NoteRepository extends CrudRepository<Note, Long> {

    List<Note> findByUser_Id(Long id);

}

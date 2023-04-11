package com.kodillafinalproject.service;

import com.kodillafinalproject.controller.NoteNotFoundException;
import com.kodillafinalproject.domain.Note;
import com.kodillafinalproject.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    public Note findNoteById(Long id) throws NoteNotFoundException {
        return noteRepository.findById(id).orElseThrow(NoteNotFoundException::new);
    }

    public Note saveNote(Note note) {
        return noteRepository.save(note);
    }

    public void deleteNoteById(Long noteId) {
        noteRepository.deleteById(noteId);
    }

    public boolean exists(Long noteId) {
        return noteRepository.existsById(noteId);
    }

    public List<Note> getAllUserNotes(Long userId) throws NoteNotFoundException {
        List<Note> noteList = noteRepository.findByUser_Id(userId);
        if (noteList.size()!=0){
            return noteList;
        }else {
            throw new NoteNotFoundException();
        }
    }
}

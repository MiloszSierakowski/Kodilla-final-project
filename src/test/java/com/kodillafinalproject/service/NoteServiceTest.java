package com.kodillafinalproject.service;

import com.kodillafinalproject.controller.NoteNotFoundException;
import com.kodillafinalproject.domain.Note;
import com.kodillafinalproject.domain.User;
import com.kodillafinalproject.repository.NoteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NoteServiceTest {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private NoteService noteService;

    @Autowired
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = userService.saveUser(new User(0L, "Janusz", "Kowalski", "Szczecin"
                , new HashSet<>(), new ArrayList<>(), new HashSet<>())
        );
    }

    @AfterEach
    void cleanUp() {
        userService.deleteUserById(user.getId());
    }

    @Test
    void findNoteById() throws NoteNotFoundException {
        Note note = new Note(-1L, "Test", "Tests1", user);

        Note savedNote = noteRepository.save(note);
        Note searchedNote = noteService.findNoteById(savedNote.getId());

        try {
            assertEquals(note.getTitle(), searchedNote.getTitle());
            assertEquals(note.getDescription(), searchedNote.getDescription());
        } finally {
            noteRepository.deleteById(savedNote.getId());
        }
    }

    @Test
    void findNoteByIdThrowNoteNotFondException() {
        Note note = new Note(-1L, "Test", "Tests1", user);

        Note savedNote = noteRepository.save(note);
        noteRepository.deleteById(savedNote.getId());

        assertThrows(NoteNotFoundException.class, () -> noteService.findNoteById(savedNote.getId()));

    }

    @Test
    void saveNote() {
        Note note = new Note(-1L, "Test", "Tests1", user);

        Note savedNote = noteService.saveNote(note);
        Optional<Note> searchedNote = noteRepository.findById(savedNote.getId());

        try {
            assertTrue(searchedNote.isPresent());
            assertEquals(note.getTitle(), searchedNote.get().getTitle());
            assertEquals(note.getDescription(), searchedNote.get().getDescription());
        } finally {
            noteRepository.deleteById(savedNote.getId());
        }
    }

    @Test
    void deleteNoteById() {
        Note note = new Note(-1L, "Test", "Tests1", user);

        Note savedNote = noteRepository.save(note);
        noteService.deleteNoteById(savedNote.getId());
        Optional<Note> searchedNote = noteRepository.findById(savedNote.getId());

        assertFalse(searchedNote.isPresent());
    }

    @Test
    void exists() {
        Note note = new Note(-1L, "Test", "Tests1", user);

        Note savedNote = noteRepository.save(note);

        try {
            assertTrue(noteService.exists(savedNote.getId()));
        } finally {
            noteService.deleteNoteById(savedNote.getId());
        }
    }

    @Test
    void getAllUserNotes() throws NoteNotFoundException {
        List<Note> noteList = List.of(
                new Note(-1L, "Test1", "Tests1", user),
                new Note(-2L, "Test2", "Tests2", user),
                new Note(-3L, "Test3", "Tests3", user)
        );

        List<Note> savedList = new ArrayList<>();

        for (Note n : noteList) {
            savedList.add(noteRepository.save(n));
        }

        List<Note> searchedNoteList = noteService.getAllUserNotes(user.getId());

        try {
            assertEquals(3, searchedNoteList.size());
            assertEquals(noteList.get(0).getTitle(), searchedNoteList.get(0).getTitle());
            assertEquals(noteList.get(1).getTitle(), searchedNoteList.get(1).getTitle());
            assertEquals(noteList.get(2).getTitle(), searchedNoteList.get(2).getTitle());
            assertEquals(noteList.get(0).getDescription(), searchedNoteList.get(0).getDescription());
            assertEquals(noteList.get(1).getDescription(), searchedNoteList.get(1).getDescription());
            assertEquals(noteList.get(2).getDescription(), searchedNoteList.get(2).getDescription());
        } finally {
            for (Note n : savedList) {
                noteService.deleteNoteById(n.getId());
            }
        }
    }

    @Test
    void getAllUserNotesThrowsNoteNotFound() {
        assertThrows(NoteNotFoundException.class, () -> noteService.getAllUserNotes(user.getId()));
    }
}
package com.kodillafinalproject.mapper;

import com.kodillafinalproject.domain.Note;
import com.kodillafinalproject.domain.NoteDto;
import com.kodillafinalproject.domain.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class NoteMapperTest {
    private static Note note;
    private static NoteDto noteDto;
    @Autowired
    private NoteMapper noteMapper;

    @BeforeAll
    static void setUp() {
        note = new Note(1L, "Test", "Testowanie mappera", new User());
        noteDto = new NoteDto(1L, "Test", "Testowanie mappera", 2L);
    }

    @Test
    void mapToNote() {
        Note mappedNote = noteMapper.mapToNote(noteDto);

        assertAll(
                () -> assertEquals(noteDto.getId(), mappedNote.getId()),
                () -> assertEquals(noteDto.getTitle(), mappedNote.getTitle()),
                () -> assertEquals(noteDto.getDescription(), mappedNote.getDescription())
        );
    }

    @Test
    void mapToNoteDto() {
        NoteDto mappedNoteDto = noteMapper.mapToNoteDto(note);

        assertAll(
                () -> assertEquals(note.getId(), mappedNoteDto.getId()),
                () -> assertEquals(note.getTitle(), mappedNoteDto.getTitle()),
                () -> assertEquals(note.getDescription(), mappedNoteDto.getDescription())
        );
    }
}
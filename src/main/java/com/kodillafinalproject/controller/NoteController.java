package com.kodillafinalproject.controller;

import com.kodillafinalproject.domain.Note;
import com.kodillafinalproject.domain.NoteDto;
import com.kodillafinalproject.mapper.NoteMapper;
import com.kodillafinalproject.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/note")
@RequiredArgsConstructor
@CrossOrigin("*")
public class NoteController {

    private final NoteMapper noteMapper;
    private final NoteService noteService;

    @GetMapping(value = "/{noteId}")
    public ResponseEntity<NoteDto> getNoteById(@PathVariable Long noteId) throws NoteNotFoundException {
        Note note = noteService.findNoteById(noteId);
        return ResponseEntity.ok(noteMapper.mapToNoteDto(note));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> creatNote(@RequestBody NoteDto noteDto) {
        Note note = noteMapper.mapToNote(noteDto);
        noteService.saveNote(note);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{noteId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NoteDto> updateNote(@PathVariable Long noteId, @RequestBody NoteDto noteDto) throws NoteNotFoundException {
        Note note = noteService.findNoteById(noteId);
        note.setTitle(noteDto.getTitle());
        note.setDescription(noteDto.getDescription());
        Note updatedNote = noteService.saveNote(note);
        return ResponseEntity.ok(noteMapper.mapToNoteDto(updatedNote));
    }

    @DeleteMapping(value = "/{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long noteId) throws NoteNotFoundException {
        if (noteService.exists(noteId)) {
            noteService.deleteNoteById(noteId);
        }else {
            throw new NoteNotFoundException();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/user/{userId}")
    public ResponseEntity<List<NoteDto>> getAllUserNote(@PathVariable Long userId) throws NoteNotFoundException {
        List<Note> noteList = noteService.getAllUserNotes(userId);
        return ResponseEntity.ok(noteMapper.mapToNoteDtoList(noteList));
    }
}

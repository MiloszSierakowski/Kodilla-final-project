package com.kodillafinalproject.controller;

import com.google.gson.Gson;
import com.kodillafinalproject.domain.Note;
import com.kodillafinalproject.domain.NoteDto;
import com.kodillafinalproject.domain.User;
import com.kodillafinalproject.mapper.NoteMapper;
import com.kodillafinalproject.service.NoteService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(NoteController.class)
class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;

    @MockBean
    private NoteMapper noteMapper;

    @Test
    void getNoteById() throws Exception {
        NoteDto noteDto = new NoteDto(1L, "Title", "Tests note", 1L);
        Note note = new Note(1L, "Title", "Tests note", new User());

        when(noteService.findNoteById(noteDto.getId())).thenReturn(note);
        when(noteMapper.mapToNoteDto(note)).thenReturn(noteDto);

        mockMvc.
                perform(MockMvcRequestBuilders
                        .get("/v1/note/{noteId}", noteDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is("Tests note")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId", Matchers.is(1)));
    }

    @Test
    void getNoteByIdThrowNoteNotFound() throws Exception {
        NoteDto noteDto = new NoteDto(1L, "Title", "Tests note", 1L);

        when(noteService.findNoteById(noteDto.getId())).thenThrow(new NoteNotFoundException());

        mockMvc.
                perform(MockMvcRequestBuilders
                        .get("/v1/note/{noteId}", noteDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    void createNote() throws Exception {
        NoteDto noteDto = new NoteDto(1L, "Title", "Tests note", 1L);
        Note note = new Note(1L, "Title", "Tests note", new User());

        when(noteMapper.mapToNote(any(NoteDto.class))).thenReturn(note);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(noteDto);

        mockMvc.
                perform(MockMvcRequestBuilders
                        .post("/v1/note")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void updateNote() throws Exception {
        NoteDto noteDto = new NoteDto(1L, "Title", "Tests note", 1L);
        Note note = new Note(1L, "", "", new User());

        when(noteService.findNoteById(any(Long.class))).thenReturn(note);
        when(noteService.saveNote(any(Note.class))).thenReturn(note);
        when(noteMapper.mapToNoteDto(any(Note.class))).thenReturn(noteDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(noteDto);

        mockMvc.
                perform(MockMvcRequestBuilders
                        .put("/v1/note/{noteId}", noteDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is("Tests note")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId", Matchers.is(1)));
    }

    @Test
    void deleteNote() throws Exception {
        when(noteService.exists(any(Long.class))).thenReturn(true);

        mockMvc.
                perform(MockMvcRequestBuilders
                        .delete("/v1/note/{noteId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void deleteNoteThrowNoteNotFound() throws Exception {
        when(noteService.exists(any(Long.class))).thenReturn(false);

        mockMvc.
                perform(MockMvcRequestBuilders
                        .delete("/v1/note/{noteId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    void getAllUserNote() throws Exception {
        Long userId = 1L;
        List<Note> noteList = List.of(
                new Note(1L, "Test1", "Tests1", new User()),
                new Note(2L, "Test2", "Tests2", new User()),
                new Note(3L, "Test3", "Tests3", new User())
        );

        List<NoteDto> noteListDto = List.of(
                new NoteDto(1L, "Test1", "Tests1", 1L),
                new NoteDto(2L, "Test2", "Tests2", 1L),
                new NoteDto(3L, "Test3", "Tests3", 1L)
        );

        when(noteService.getAllUserNotes(any(Long.class))).thenReturn(noteList);
        when(noteMapper.mapToNoteDtoList(noteList)).thenReturn(noteListDto);

        mockMvc.
                perform(MockMvcRequestBuilders
                        .get("/v1/note/user/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is("Test1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title", Matchers.is("Test2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].title", Matchers.is("Test3")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description", Matchers.is("Tests1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].description", Matchers.is("Tests2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].description", Matchers.is("Tests3")));
    }

    @Test
    void getAllUserNoteThrowNoteNotFound() throws Exception {
        Long userId = 1L;
        when(noteService.getAllUserNotes(any(Long.class))).thenThrow(new NoteNotFoundException());
        mockMvc.
                perform(MockMvcRequestBuilders
                        .get("/v1/note/user/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }
}
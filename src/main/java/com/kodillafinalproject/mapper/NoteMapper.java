package com.kodillafinalproject.mapper;

import com.kodillafinalproject.domain.Note;
import com.kodillafinalproject.domain.NoteDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NoteMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(ignore = true, target = "user")
    Note mapToNote(NoteDto noteDto);

    @InheritInverseConfiguration(name = "mapToNote")
    NoteDto mapToNoteDto(Note note);

    List<Note> mapToNoteList(List<NoteDto> noteDtos);
    List<NoteDto> mapToNoteDtoList(List<Note> noteDtos);
}

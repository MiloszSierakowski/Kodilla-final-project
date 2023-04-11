package com.kodillafinalproject.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalHttpErrorHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException exception) {
        return new ResponseEntity<>("User doesn't exists", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoRequiredPersonDataException.class)
    public ResponseEntity<Object> handleNoRequiredPersonDataException(NoRequiredPersonDataException exception) {
        return new ResponseEntity<>("You have not provided the first or last " +
                "name of the person you want to find. Please enter a username.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoteNotFoundException.class)
    public ResponseEntity<Object> handleNoteNotFoundException(NoteNotFoundException exception) {
        return new ResponseEntity<>("Note doesn't exists", HttpStatus.NOT_FOUND);
    }
}

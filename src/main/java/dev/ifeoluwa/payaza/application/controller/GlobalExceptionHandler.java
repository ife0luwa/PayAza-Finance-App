package dev.ifeoluwa.payaza.application.controller;

import dev.ifeoluwa.payaza.application.exception.EmailAlreadyExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author on 24/03/2023
 * @project
 */
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> handleExceptions(EmailAlreadyExistsException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}

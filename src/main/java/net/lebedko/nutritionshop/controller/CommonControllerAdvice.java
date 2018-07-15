package net.lebedko.nutritionshop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class CommonControllerAdvice {
    @ExceptionHandler
    public ResponseEntity notFound(NoSuchElementException notFound) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(notFound.getMessage());
    }
}

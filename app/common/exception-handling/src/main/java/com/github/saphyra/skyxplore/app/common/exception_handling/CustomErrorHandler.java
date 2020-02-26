package com.github.saphyra.skyxplore.app.common.exception_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class CustomErrorHandler {
    @ExceptionHandler({MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<String> handleBadRequest(RuntimeException e) {
        log.warn("Bad Request: ", e);
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<String> handleInvalidArgument(Exception e) {
        log.warn("Invalid parameter: ", e);
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handleNotSupported(HttpRequestMethodNotSupportedException e) {
        log.error("Http Method not allowed", e);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(e.getMessage());
    }
}

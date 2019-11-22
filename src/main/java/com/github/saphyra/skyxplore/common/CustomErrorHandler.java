package com.github.saphyra.skyxplore.common;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.github.saphyra.skyxplore.Application;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice(basePackageClasses = Application.class)
@Slf4j
public class CustomErrorHandler {
    @ExceptionHandler({MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<String> handleArgumentTypeMismatch(RuntimeException e) {
        log.warn("Bad Request: ", e);
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

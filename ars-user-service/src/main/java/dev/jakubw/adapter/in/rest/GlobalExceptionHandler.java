package dev.jakubw.adapter.in.rest;

import dev.jakubw.adapter.in.dto.ErrorMessageDto;
import dev.jakubw.config.exception.RecommendationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<ErrorMessageDto> handleIllegalAccessException(IllegalArgumentException ex){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new ErrorMessageDto(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.name(),
                ex.getMessage()
        ),status);
    }

    @ExceptionHandler(RecommendationException.class)
    public ResponseEntity<ErrorMessageDto> handleRecommendationException(RecommendationException ex){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new ErrorMessageDto(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.name(),
                ex.getMessage()
        ),status);
    }
}

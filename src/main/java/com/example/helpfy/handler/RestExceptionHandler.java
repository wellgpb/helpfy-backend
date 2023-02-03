package com.example.helpfy.handler;

import com.example.helpfy.dtos.ExceptionResponse;
import com.example.helpfy.exceptions.BadRequestException;
import com.example.helpfy.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequestException(BadRequestException bre) {
        return buildExceptionResponse(bre, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(NotFoundException nfe) {
        return buildExceptionResponse(nfe, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException (MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<String> messages = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());

        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message(messages)
                        .build(), HttpStatus.BAD_REQUEST
        );
    }

    private ResponseEntity<ExceptionResponse> buildExceptionResponse(RuntimeException ex, HttpStatus statusCode) {
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .status(statusCode.value())
                        .message(new ArrayList<>(Collections.singletonList(ex.getMessage())))
                        .build(), statusCode
        );
    }
}

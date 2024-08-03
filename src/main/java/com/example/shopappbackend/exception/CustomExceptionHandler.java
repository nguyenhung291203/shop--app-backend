package com.example.shopappbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorException<String>> handleNotFoundException(NotFoundException ex, WebRequest req) {
        ErrorException<String> errorException = new ErrorException<>(ex.getMessage(), req.getDescription(false));
        return new ResponseEntity<>(errorException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorException<String>> handleAlreadyExistedException(BadRequestException ex, WebRequest req) {
        ErrorException<String> errorException = new ErrorException<>(ex.getMessage(), req.getDescription(false));
        return new ResponseEntity<>(errorException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorException<String>> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest req) {
        ErrorException<String> errorException = new ErrorException<>(ex.getMessage(), req.getDescription(false));
        return new ResponseEntity<>(errorException, HttpStatus.CONFLICT);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorException<List<String>>> handleValidationException(MethodArgumentNotValidException ex, WebRequest req) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
//            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.add(errorMessage);
        });
        ErrorException<List<String>> errorException = new ErrorException<>(errors, req.getDescription(false));
        return new ResponseEntity<>(errorException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorException<String>> globleExcpetionHandler(Exception ex, WebRequest request) {
        ErrorException<String> errorException = new ErrorException<>(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorException<String>> handleUnauthorizedException(UnauthorizedException ex, WebRequest request) {
        ErrorException<String> errorDetails = new ErrorException<>(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }
}
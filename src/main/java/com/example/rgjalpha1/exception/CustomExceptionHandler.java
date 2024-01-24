package com.example.rgjalpha1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;


// This class is used to handle exceptions thrown by the @Valid annotation
@ControllerAdvice
public class CustomExceptionHandler {

    // This method handles exceptions thrown by the @Valid annotation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordLengthException.class)
    public ResponseEntity<String> handlePasswordLengthException(PasswordLengthException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<Object> handlePasswordMismatchException(PasswordMismatchException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("password", ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoPhotoDataException.class)
    public ResponseEntity<String> handleNoPhotoDataException(NoPhotoDataException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequestException(BadRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<String> handleRecordNotFoundException(RecordNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(UsernameExistsException.class)
    public ResponseEntity<String> handleUsernameExistsException(UsernameExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
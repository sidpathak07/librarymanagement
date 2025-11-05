package com.chapter3.librarymanagement.advices;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleEntityNotFound(EntityNotFoundException ex) {
        ApiError error = ApiError.builder()
                .httpCode(HttpStatus.NO_CONTENT.value())
                .status(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .errors(List.of("Requested entity not found in database"))
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(error));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationError(MethodArgumentNotValidException ex) {
        List<String> errorList = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(ObjectError->ObjectError.getDefaultMessage())
                .toList();

        ApiError error = ApiError.builder()
                .httpCode(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST)
                .message("Validation failed")
                .errors(errorList)
                .build();

        return ResponseEntity.badRequest().body(new ApiResponse<>(error));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGenericError(Exception ex) {
        ApiError error = ApiError.builder()
                .httpCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("Something went wrong")
                .errors(List.of(ex.getMessage()))
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(error));
    }
}

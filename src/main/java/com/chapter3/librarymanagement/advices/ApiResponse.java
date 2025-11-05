package com.chapter3.librarymanagement.advices;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private T data;
    private ApiError apiError;
    private String message;
    private Boolean status;
    private HttpStatus httpStatus;
    private int httpCode;
    private LocalDateTime responseTime = LocalDateTime.now();

    public ApiResponse(T data) {
        this.data = data;
        this.status = true;
        this.message = "Success";
        this.httpCode = HttpStatus.OK.value();
        this.httpStatus = HttpStatus.OK;
    }

    public ApiResponse(T data, String message) {
        this.data = data;
        this.status = true;
        this.message = message;
        this.httpCode = HttpStatus.OK.value();
        this.httpStatus = HttpStatus.OK;
    }

    public ApiResponse(ApiError apiError) {
        this.apiError = apiError;
        this.status = false;
        this.message = apiError.getMessage();
        this.httpStatus = apiError.getStatus();
        this.httpCode = apiError.getHttpCode();
    }
}


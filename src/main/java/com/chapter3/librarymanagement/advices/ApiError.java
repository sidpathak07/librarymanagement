package com.chapter3.librarymanagement.advices;


import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
public class ApiError {
    private int httpCode;
    private HttpStatus status;   // enum
    private String message;
    private List<String> errors;
}

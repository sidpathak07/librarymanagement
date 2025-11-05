package com.chapter3.librarymanagement.controllers;

import com.chapter3.librarymanagement.advices.ApiResponse;
import com.chapter3.librarymanagement.dtos.BookDTO;
import com.chapter3.librarymanagement.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/books")
public class BookController {
    private final BookService bookService;
    @GetMapping("/{bookId}")
    public ResponseEntity<ApiResponse<BookDTO>> getBookById(@PathVariable Long bookId){
        BookDTO bookDTO = bookService.getBookById(bookId);
        return ResponseEntity.ok(new ApiResponse<>(bookDTO));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BookDTO>> createBook(@RequestBody BookDTO bookDTO,@RequestParam(required = false) Long authorId ){
        BookDTO savedBookDTO = bookService.createBook(bookDTO,authorId);
        return ResponseEntity.ok(new ApiResponse<>(savedBookDTO));
    }



}

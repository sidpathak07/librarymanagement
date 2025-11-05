package com.chapter3.librarymanagement.controllers;

import com.chapter3.librarymanagement.advices.ApiResponse;
import com.chapter3.librarymanagement.dtos.AuthorDTO;
import com.chapter3.librarymanagement.dtos.MiniBookDTO;
import com.chapter3.librarymanagement.exceptions.EntityNotFoundException;
import com.chapter3.librarymanagement.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;
    @GetMapping("/{authorId}")
    public ResponseEntity<ApiResponse<AuthorDTO>> getAuthor(@PathVariable Long authorId) throws EntityNotFoundException {
        AuthorDTO authorDTO = authorService.getAuthorById(authorId);
        return ResponseEntity.ok(new ApiResponse<>(authorDTO));
    }

    @GetMapping("/authorName/{authorName}")
    public ResponseEntity<ApiResponse<List<AuthorDTO>>>getAuthorsByName(@PathVariable String authorName) throws EntityNotFoundException {
        List<AuthorDTO> authorDTOList = authorService.getAuthorByName(authorName);
        return ResponseEntity.ok(new ApiResponse<>(authorDTOList));
    }

    @GetMapping("/booksByAuthor/{authorId}")
    public ResponseEntity<ApiResponse<List<MiniBookDTO>>> getBooksByAuthorId(@PathVariable Long authorId){
        List<MiniBookDTO> bookDTOList = authorService.getBooksByAuthorId(authorId);
        return ResponseEntity.ok(new ApiResponse<>(bookDTOList));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AuthorDTO>> createAuthor(@RequestBody AuthorDTO authorDTO) throws Exception {
        AuthorDTO createdAuthor = authorService.createAuthor(authorDTO);
        return ResponseEntity.ok(new ApiResponse<>(createdAuthor));
    }

    @PatchMapping("/{authorId}/{bookId}")
    public ResponseEntity<ApiResponse<AuthorDTO>> addBookToAuthor(@PathVariable Long authorId,@PathVariable Long bookId) throws EntityNotFoundException {
        AuthorDTO updatedAuthor = authorService.addBookToAuthor(bookId,authorId);
        return ResponseEntity.ok(new ApiResponse<>(updatedAuthor));
    }

}

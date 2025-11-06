package com.chapter3.librarymanagement.controllers;

import com.chapter3.librarymanagement.advices.ApiResponse;
import com.chapter3.librarymanagement.dtos.BookDTO;
import com.chapter3.librarymanagement.projections.MiniBookProjection;
import com.chapter3.librarymanagement.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/getBooksByAuthor")
    public ResponseEntity<ApiResponse<List<MiniBookProjection>>> getBooksForAuthorId(@RequestParam Long authorId){
        List<MiniBookProjection> bookProjectionList = bookService.getBooksByAuthor(authorId);
        return ResponseEntity.ok(new ApiResponse<>(bookProjectionList));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<BookDTO>> getBookByTitle(@RequestParam String title){
        BookDTO bookDTO = bookService.getBookByTitle(title);
        return ResponseEntity.ok(new ApiResponse<>(bookDTO));
    }

    @GetMapping("/getAllBooks")
    public ResponseEntity<ApiResponse<List<MiniBookProjection>>> getAllBooks(){
        List<MiniBookProjection> bookProjectionList = bookService.getAllBooks();
     //   List<BookDTO> bookDTOList = bookService.getAllBookEntities();
        return ResponseEntity.ok(new ApiResponse<>(bookProjectionList));
    }

    @GetMapping("/getAllBookEntities")
    public ResponseEntity<ApiResponse<List<BookDTO>>> getAllBookEntities(){
       // List<MiniBookProjection> bookProjectionList = bookService.getAllBooks();
        List<BookDTO> bookDTOList = bookService.getAllBookEntities();
        return ResponseEntity.ok(new ApiResponse<>(bookDTOList));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BookDTO>> createBook(@RequestBody BookDTO bookDTO,@RequestParam(required = false) Long authorId ){
        BookDTO savedBookDTO = bookService.createBook(bookDTO,authorId);
        return ResponseEntity.ok(new ApiResponse<>(savedBookDTO));
    }





}

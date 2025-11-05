package com.chapter3.librarymanagement.services;

import com.chapter3.librarymanagement.dtos.BookDTO;
import com.chapter3.librarymanagement.entities.AuthorEntity;
import com.chapter3.librarymanagement.entities.BookEntity;
import com.chapter3.librarymanagement.repositories.AuthorRepository;
import com.chapter3.librarymanagement.repositories.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public BookDTO getBookById(Long bookId) {
        BookEntity bookEntity = bookRepository.findById(bookId).orElseThrow(()->new EntityNotFoundException("Book with id: "+bookId+" not found"));
        return modelMapper.map(bookEntity,BookDTO.class);
    }

    @Transactional
    public BookDTO createBook(BookDTO bookDTO,Long authorId){
        if(bookDTO == null){
            throw new IllegalArgumentException("BookTitle and Author not mentioned");
        }
        BookEntity bookEntity = modelMapper.map(bookDTO,BookEntity.class);
        AuthorEntity authorEntity = null;
        if(authorId!=null){
            authorEntity = authorRepository.findById(authorId).orElseThrow(()-> new EntityNotFoundException("Author with id: "+authorId+" not found"));
            bookEntity.setAuthor(authorEntity);
        }
        BookEntity savedBook = bookRepository.save(bookEntity);
        return modelMapper.map(savedBook,BookDTO.class);
    }

    @Transactional
    public BookDTO updateBookTitle(Long bookId,BookDTO bookDTO){
        BookEntity bookEntity = bookRepository.findById(bookId).orElseThrow(()->new EntityNotFoundException("Book with id: "+bookId+" not found"));
        if(bookDTO.getBookTitle()!= null){
            bookEntity.setBookTitle(bookDTO.getBookTitle());
        }
        return modelMapper.map(bookEntity,BookDTO.class);
    }

    @Transactional
    public BookDTO updateBookAuthor(Long bookId,Long authorId) {
        BookEntity bookEntity = bookRepository.findById(bookId).orElseThrow(()->new EntityNotFoundException("Book with id: "+bookId+" not found"));
        AuthorEntity authorEntity = authorRepository.findById(authorId).orElseThrow(()->new EntityNotFoundException("Author with id: "+bookId+" not found"));
        bookEntity.setAuthor(authorEntity);
        return modelMapper.map(bookEntity,BookDTO.class);
    }

}

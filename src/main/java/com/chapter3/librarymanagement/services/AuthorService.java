package com.chapter3.librarymanagement.services;

import com.chapter3.librarymanagement.dtos.AuthorDTO;
import com.chapter3.librarymanagement.entities.AuthorEntity;
import com.chapter3.librarymanagement.entities.BookEntity;
import com.chapter3.librarymanagement.exceptions.EntityNotFoundException;
import com.chapter3.librarymanagement.repositories.AuthorRepository;
import com.chapter3.librarymanagement.repositories.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    public AuthorDTO getAuthorById(Long id) throws EntityNotFoundException {
        AuthorEntity authorEntity = authorRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Author with id: "+id+" not found"));
        return modelMapper.map(authorEntity,AuthorDTO.class);
    }

    public AuthorDTO createAuthor(AuthorDTO authorDTO) {
        if(authorDTO==null){
            throw new IllegalArgumentException("Author cannot be empty or null");
        }
        AuthorEntity authorEntity = modelMapper.map(authorDTO,AuthorEntity.class);
        AuthorEntity saved= authorRepository.save(authorEntity);
        return modelMapper.map(saved,AuthorDTO.class);
    }

    @Transactional
    public AuthorDTO addBookToAuthor(Long bookId, Long authorId) throws EntityNotFoundException {
        AuthorEntity authorEntity = authorRepository.findById(authorId)
                .orElseThrow(()-> new EntityNotFoundException("Author with id: "+authorId+" not found"));
        BookEntity bookEntity = bookRepository.findById(bookId)
                .orElseThrow(()-> new EntityNotFoundException("Book with id: "+bookId+" not found"));
        authorEntity.getBooks().add(bookEntity);
        return modelMapper.map(authorEntity,AuthorDTO.class);
    }

    public List<AuthorDTO> getAuthorByName(String name){
        List<AuthorEntity> authorEntityList = authorRepository.findByName(name);
        return authorEntityList
                .stream()
                .map(authorEntity -> modelMapper.map(authorEntity,AuthorDTO.class))
                .collect(Collectors.toList());
    }

    /*public List<MiniBookDTO> getBooksByAuthorId(Long authorId) {
        List<BookEntity> bookEntities = authorRepository.findBooksByAuthorId(authorId);
        return bookEntities.stream()
                .map(bookEntity -> modelMapper.map(bookEntity, MiniBookDTO.class))
                .collect(Collectors.toList());
    }*/
}

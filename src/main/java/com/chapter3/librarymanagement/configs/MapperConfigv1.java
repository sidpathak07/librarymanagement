package com.chapter3.librarymanagement.configs;

import com.chapter3.librarymanagement.dtos.AuthorDTO;
import com.chapter3.librarymanagement.dtos.BookDTO;
import com.chapter3.librarymanagement.dtos.MiniAuthorDTO;
import com.chapter3.librarymanagement.dtos.MiniBookDTO;
import com.chapter3.librarymanagement.entities.AuthorEntity;
import com.chapter3.librarymanagement.entities.BookEntity;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class MapperConfigv1 {

    public ModelMapper getModelMapper(){
        ModelMapper mapper = new ModelMapper();

        // --- AuthorEntity → AuthorDTO ---
        mapper.typeMap(AuthorEntity.class, AuthorDTO.class).addMappings(m -> {
            // Convert List<Book> → List<MiniBookDTO>
            m.using(ctx -> ((List<BookEntity>) ctx.getSource()).stream()
                            .map(book -> mapper.map(book, MiniBookDTO.class))
                            .collect(Collectors.toList()))
                    .map(AuthorEntity::getBooks, AuthorDTO::setBooks);

        });

        // --- BookEntity -> BookDTO
        mapper.typeMap(BookEntity.class, BookDTO.class).addMappings(m->{
            m.using(ctx->mapper.map(ctx.getSource(), MiniAuthorDTO.class))
                    .map(BookEntity::getAuthor,BookDTO::setAuthor);
        });

        // --- AuthorDTO → AuthorEntity ---
        mapper.typeMap(AuthorDTO.class, AuthorEntity.class).addMappings(m ->
                m.using((Converter<List<BookDTO>, List<BookEntity>>) ctx -> {
                    List<BookDTO> sourceBooks = ctx.getSource();
                    if (sourceBooks == null) return Collections.emptyList();
                    return sourceBooks.stream()
                            .map(bookDTO -> mapper.map(bookDTO, BookEntity.class))
                            .collect(Collectors.toList());
                }).map(AuthorDTO::getBooks, AuthorEntity::setBooks)
        );

        // --- BookDTO → BookEntity ---
        mapper.typeMap(BookDTO.class, BookEntity.class).addMappings(m ->
                m.using(ctx -> ctx.getSource() == null ? null : mapper.map(ctx.getSource(), AuthorEntity.class))
                        .map(BookDTO::getAuthor, BookEntity::setAuthor)
        );
        return mapper;
    }
}

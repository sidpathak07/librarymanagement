package com.chapter3.librarymanagement.configs;

import com.chapter3.librarymanagement.dtos.*;
import com.chapter3.librarymanagement.entities.AuthorEntity;
import com.chapter3.librarymanagement.entities.BookEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper mapper = new ModelMapper();

        // stop auto nested mapping
        mapper.getConfiguration()
                .setImplicitMappingEnabled(false)
                .setAmbiguityIgnored(true);

        // ----------- AuthorEntity -> AuthorDTO -----------
        TypeMap<AuthorEntity, AuthorDTO> authorMap =
                mapper.createTypeMap(AuthorEntity.class, AuthorDTO.class);

        authorMap.addMapping(AuthorEntity::getId, AuthorDTO::setId);
        authorMap.addMapping(AuthorEntity::getName, AuthorDTO::setName);
        authorMap.addMappings(m -> m.skip(AuthorDTO::setBooks)); // handled manually

        authorMap.setPostConverter(ctx -> {
            AuthorEntity src = ctx.getSource();
            AuthorDTO dest = ctx.getDestination();

            if (src.getBooks() != null) {
                dest.setBooks(
                        src.getBooks()
                                .stream()
                                .map(b -> mapper.map(b, MiniBookDTO.class))
                                .toList()
                );
            }
            return dest;
        });

        // ----------- BookEntity -> BookDTO -----------
        TypeMap<BookEntity, BookDTO> bookMap =
                mapper.createTypeMap(BookEntity.class, BookDTO.class);

        bookMap.addMapping(BookEntity::getId, BookDTO::setId);
        bookMap.addMapping(BookEntity::getBookTitle, BookDTO::setBookTitle);
        bookMap.addMapping(BookEntity::getCreatedAt, BookDTO::setCreatedAt);
        bookMap.addMappings(m -> m.skip(BookDTO::setAuthor)); // handled manually

        bookMap.setPostConverter(ctx -> {
            BookEntity src = ctx.getSource();
            BookDTO dest = ctx.getDestination();

            if (src.getAuthor() != null) {
                dest.setAuthor(mapper.map(src.getAuthor(), MiniAuthorDTO.class));
            }
            return dest;
        });

        // ----------- AuthorDTO -> AuthorEntity -----------
        TypeMap<AuthorDTO, AuthorEntity> authorDtoMap =
                mapper.createTypeMap(AuthorDTO.class, AuthorEntity.class);

        authorDtoMap.addMapping(AuthorDTO::getId, AuthorEntity::setId);
        authorDtoMap.addMapping(AuthorDTO::getName, AuthorEntity::setName);
        authorDtoMap.addMappings(m -> m.skip(AuthorEntity::setBooks));

        authorDtoMap.setPostConverter(ctx -> {
            AuthorDTO src = ctx.getSource();
            AuthorEntity dest = ctx.getDestination();

            if (src.getBooks() != null) {
                dest.setBooks(
                        src.getBooks()
                                .stream()
                                .map(b -> mapper.map(b, BookEntity.class))
                                .toList()
                );
            }
            return dest;
        });


// ----------- BookDTO -> BookEntity -----------
        TypeMap<BookDTO, BookEntity> bookDtoMap =
                mapper.createTypeMap(BookDTO.class, BookEntity.class);

        bookDtoMap.addMapping(BookDTO::getId, BookEntity::setId);
        bookDtoMap.addMapping(BookDTO::getBookTitle, BookEntity::setBookTitle);
        bookDtoMap.addMapping(BookDTO::getCreatedAt, BookEntity::setCreatedAt);
        bookDtoMap.addMappings(m -> m.skip(BookEntity::setAuthor));

        bookDtoMap.setPostConverter(ctx -> {
            BookDTO src = ctx.getSource();
            BookEntity dest = ctx.getDestination();

            if (src.getAuthor() != null) {
                dest.setAuthor(mapper.map(src.getAuthor(), AuthorEntity.class));
            }
            return dest;
        });

        return mapper;
    }
}

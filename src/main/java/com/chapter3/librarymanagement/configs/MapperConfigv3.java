package com.chapter3.librarymanagement.configs;

import com.chapter3.librarymanagement.dtos.AuthorDTO;
import com.chapter3.librarymanagement.dtos.BookDTO;
import com.chapter3.librarymanagement.dtos.MiniAuthorDTO;
import com.chapter3.librarymanagement.dtos.MiniBookDTO;
import com.chapter3.librarymanagement.entities.AuthorEntity;
import com.chapter3.librarymanagement.entities.BookEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


public class MapperConfigv3 {


    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        //
        // 1) AuthorEntity → AuthorDTO
        //
        mapper.createTypeMap(AuthorEntity.class, AuthorDTO.class)
                .addMappings(m -> {
                    m.map(AuthorEntity::getId, AuthorDTO::setId);
                    m.map(AuthorEntity::getName, AuthorDTO::setName);
                    // books list auto converts to MiniBookDTO
                });


        //
        // 2) BookEntity → BookDTO
        //
        mapper.createTypeMap(BookEntity.class, BookDTO.class)
                .addMappings(m -> {
                    m.map(BookEntity::getId, BookDTO::setId);
                    m.map(BookEntity::getBookTitle, BookDTO::setBookTitle);
                    m.map(BookEntity::getCreatedAt, BookDTO::setCreatedAt);

                    // AuthorEntity → MiniAuthorDTO
                    m.<MiniAuthorDTO>map(src ->
                                    new MiniAuthorDTO(src.getAuthor().getId(), src.getAuthor().getName()),
                            BookDTO::setAuthor);
                });


        //
        // 3) AuthorEntity → MiniAuthorDTO
        //
        mapper.createTypeMap(AuthorEntity.class, MiniAuthorDTO.class)
                .addMappings(m -> {
                    m.map(AuthorEntity::getId, MiniAuthorDTO::setId);
                    m.map(AuthorEntity::getName, MiniAuthorDTO::setName);
                });


        //
        // 4) BookEntity → MiniBookDTO
        //
        mapper.createTypeMap(BookEntity.class, MiniBookDTO.class)
                .addMappings(m -> {
                    m.map(BookEntity::getId, MiniBookDTO::setId);
                    m.map(BookEntity::getBookTitle, MiniBookDTO::setBookTitle);
                    m.map(BookEntity::getCreatedAt, MiniBookDTO::setCreatedAt);
                });

        return mapper;
    }
}


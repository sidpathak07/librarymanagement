package com.chapter3.librarymanagement.configs;

import com.chapter3.librarymanagement.dtos.AuthorDTO;
import com.chapter3.librarymanagement.dtos.BookDTO;
import com.chapter3.librarymanagement.dtos.MiniAuthorDTO;
import com.chapter3.librarymanagement.dtos.MiniBookDTO;
import com.chapter3.librarymanagement.entities.AuthorEntity;
import com.chapter3.librarymanagement.entities.BookEntity;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class MapperConfigv2 {

    public ModelMapper modelMapper(){
        ModelMapper mapper = new ModelMapper();

        mapper.typeMap(AuthorEntity.class, AuthorDTO.class).addMappings(m->{
            m.using(SafeConverters.safeListMap(mapper,MiniBookDTO.class))
                    .map(AuthorEntity::getBooks, AuthorDTO::setBooks);
        });

        mapper.typeMap(BookEntity.class, BookDTO.class).addMappings(m->{
            m.using(SafeConverters.safeMap(mapper, MiniAuthorDTO.class))
                    .map(BookEntity::getAuthor,BookDTO::setAuthor);
        });

        mapper.typeMap(AuthorDTO.class, AuthorEntity.class).addMappings(m->{
            m.using(SafeConverters.safeListMap(mapper, BookEntity.class))
                    .map(AuthorDTO::getBooks,AuthorEntity::setBooks);
        });

        mapper.typeMap(BookDTO.class, BookEntity.class).addMappings(m->{
            m.using(SafeConverters.safeMap(mapper, AuthorEntity.class))
                    .map(BookDTO::getAuthor,BookEntity::setAuthor);
        });
        return mapper;
    }
}

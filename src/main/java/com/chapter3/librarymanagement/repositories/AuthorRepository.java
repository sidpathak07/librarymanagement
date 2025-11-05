package com.chapter3.librarymanagement.repositories;

import com.chapter3.librarymanagement.entities.AuthorEntity;
import com.chapter3.librarymanagement.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity,Long> {
    @Query("select a from AuthorEntity a where name like %:name%")
    List<AuthorEntity> findByName(@Param("name")String name);

    @Query("select a.books from AuthorEntity a where a.id=:authorId")
    List<BookEntity> findBooksByAuthorId(@Param("authorId") Long authorId);
}

package com.chapter3.librarymanagement.repositories;

import com.chapter3.librarymanagement.entities.BookEntity;
import com.chapter3.librarymanagement.projections.MiniBookProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Book;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity,Long> {
    @Query("select a.id as id,a.bookTitle as bookTitle,a.createdAt as createdAt from BookEntity a where a.author.id=:authorId")
    List<MiniBookProjection> getBookByAuthor(@Param("authorId") Long authorId);

    BookEntity findByBookTitle(String bookTitle);

    @Query("select a.id as id,a.bookTitle as bookTitle,a.createdAt as createdAt from BookEntity a order by a.createdAt desc")
    List<MiniBookProjection> getAllBooks();

    @Query("select a from BookEntity a ")
    List<BookEntity> getAllBookEntities();
}

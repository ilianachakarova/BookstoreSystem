package com.example.bookshopsystem.repositories;

import com.example.bookshopsystem.entities.Author;
import com.example.bookshopsystem.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    List<Book> findAllByReleaseDateAfter(LocalDate localDate);
    //List<Book>findAllByAuthorOrderByReleaseDateDesc(Author author);
    List<Book>findAllByAuthorOrderByReleaseDateDescTitle(Author author);
    @Query("select b.author from Book as b where b.releaseDate > ?1")
    List<Book> getAllAuthorsWithAtLeastOneBookAfter1990(LocalDate date);
}

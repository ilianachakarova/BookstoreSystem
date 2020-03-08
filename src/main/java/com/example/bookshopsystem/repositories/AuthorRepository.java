package com.example.bookshopsystem.repositories;

import com.example.bookshopsystem.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query("select a from Author as a order by a.books.size desc ")
    List<Author>findAuthorByCountOfBook();
    Author findByFirstNameAndLastName(String firstName, String lastName);
   // List<Author>findAuthorsByBooksBeforeReleaseDate(LocalDate date);
    List<Author>findAllByFirstNameEndingWith(String chars);

    @Query("select concat(a.firstName, ' ', a.lastName), sum(b.copies) from Author as a inner join a.books as b group by a.firstName,a.lastName order by sum(b.copies) desc ")
    List<Object[]>findTotalBookCopiesPerAuthor();
}

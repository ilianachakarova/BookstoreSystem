package com.example.bookshopsystem.repositories;

import com.example.bookshopsystem.entities.AgeRestriction;
import com.example.bookshopsystem.entities.Author;
import com.example.bookshopsystem.entities.Book;
import com.example.bookshopsystem.entities.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    List<Book> findAllByReleaseDateAfter(LocalDate localDate);
    //List<Book>findAllByAuthorOrderByReleaseDateDesc(Author author);
    List<Book>findAllByAuthorOrderByReleaseDateDescTitle(Author author);
    @Query("select b.author from Book as b where b.releaseDate > ?1")
    List<Book> getAllAuthorsWithAtLeastOneBookAfter1990(LocalDate date);
    List<Book> findAllByAgeRestrictionEquals(AgeRestriction ageRestriction);
    List<Book>findAllByEditionTypeEqualsAndCopiesBefore(EditionType editionType, int copies);
    List<Book>findAllByPriceBeforeOrPriceAfter(BigDecimal price1, BigDecimal price2);
}

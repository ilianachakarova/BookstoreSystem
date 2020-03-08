package com.example.bookshopsystem.repositories;

import com.example.bookshopsystem.entities.AgeRestriction;
import com.example.bookshopsystem.entities.Author;
import com.example.bookshopsystem.entities.Book;
import com.example.bookshopsystem.entities.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    List<Book> findAllByReleaseDateAfter(LocalDate localDate);
    List<Book>findAllByAuthorOrderByReleaseDateDescTitle(Author author);
    @Query("select b.author from Book as b where b.releaseDate > ?1")
    List<Book> getAllAuthorsWithAtLeastOneBookAfter1990(LocalDate date);
    List<Book> findAllByAgeRestrictionEquals(AgeRestriction ageRestriction);
    List<Book>findAllByEditionTypeEqualsAndCopiesBefore(EditionType editionType, int copies);
    List<Book>findAllByPriceBeforeOrPriceAfter(BigDecimal price1, BigDecimal price2);
    List<Book>findAllByReleaseDateBeforeOrReleaseDateAfter(LocalDate date1, LocalDate date2);
    List<Book>findAllByReleaseDateBefore(LocalDate date);
    List<Book>findAllByTitleContaining(String chars);
    @Query("select b from Book as b join b.author as a where a.lastName like concat(:str,'%') ")
    List<Book>findBooksByAuthorLastName(@Param(value = "str")String str);
    @Query("select b from com.example.bookshopsystem.entities.Book as b where length(b.title) >=:num ")
    List<Book>findBooksByTitleLengthGreaterThan(int num);
    @Modifying
    @Query("UPDATE Book as b set b.copies = b.copies + :copies where b.releaseDate >:date")
    int updateAllBooksAfterGivenDate(@Param(value = "date") LocalDate date,@Param(value = "copies") int copies);
    Book findBooksByTitleEquals(String title);

    @Query("delete from Book as b where b.copies < :minCopies")
    @Modifying
    Integer removeBooksWithLessCopiesThan(@Param("minCopies") int minCopies);
}

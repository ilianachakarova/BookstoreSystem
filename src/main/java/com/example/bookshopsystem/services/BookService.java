package com.example.bookshopsystem.services;

import com.example.bookshopsystem.entities.Book;

import java.io.IOException;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;
    List<Book> getAllBooksAfter2000();
    List<Book> getAllBooksFromAuthorOrderByReleaseDate();
    List<Book>getAllAuthorsWithBooksAfter1990();
    List<String>getAllBooksWithAgeRestriction(String ageRestriction);
    List<String>getAllBooksGoldenEditionCopiesLessThan5000();
    List<String> getAllBooksWithPriceLower5Bigger40();
    List<String> getAllBooksNotReleasedInYear(String year);
    List<String> getAllBooksReleasedBeforeDate(String date);
    List<String> getAllBooksContaining(String chars);
    List<String>getAllBooksByAuthorLastNameStartingWith(String str);
    int getAllBooksWhoseTitleIsLongerThan(int number);
    int updateBookCopiesAfterDate(String date, int copies);
    String printInfoReducedBook(String title);
    int removeBooksWithCopiesLessThan(int minCopies);
}

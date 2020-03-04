package com.example.bookshopsystem.services;

import com.example.bookshopsystem.entities.Book;

import java.io.IOException;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;
    List<Book> getAllBooksAfter2000();
    List<Book> getAllBooksFromAuthorOrderByReleaseDate();
    List<Book>getAllAuthorsWithBooksAfter1990();
}

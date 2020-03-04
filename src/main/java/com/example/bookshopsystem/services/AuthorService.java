package com.example.bookshopsystem.services;

import com.example.bookshopsystem.entities.Author;

import java.io.IOException;
import java.util.List;


public interface AuthorService {
    void seedAuthors() throws IOException;

    int getAllAuthorsCount();

    Author findAuthorById(int id);

    List<Author> findAllAuthorsByBookSize();

    Author findAuthorByFirstAndLastName(String firstName, String lastName);

   // List<Author> findAuthorsWithAtLeastOneBookBefore1990();


}

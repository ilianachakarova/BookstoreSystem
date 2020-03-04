package com.example.bookshopsystem.services.impl;

import com.example.bookshopsystem.constants.GlobalConstants;
import com.example.bookshopsystem.entities.Author;
import com.example.bookshopsystem.repositories.AuthorRepository;
import com.example.bookshopsystem.services.AuthorService;
import com.example.bookshopsystem.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final FileUtil fileUtil;
    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, FileUtil fileUtil) {
        this.authorRepository = authorRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedAuthors() throws IOException {
        String [] fileContent =
                this.fileUtil.readFileContent(GlobalConstants.AUTHOR_FILE_PATH);

        Arrays.stream(fileContent).forEach(r->{
            String [] tokens = r.split("\\s+");
            String firstName = tokens[0];
            String lastName = tokens[1];

            Author author = new Author(firstName,lastName);
            this.authorRepository.saveAndFlush(author);
        });
    }

    @Override
    public int getAllAuthorsCount() {
        return (int) this.authorRepository.count();
    }

    @Override
    public Author findAuthorById(int id) {
        return this.authorRepository.getOne((long)id);
    }

    @Override
    public List<Author> findAllAuthorsByBookSize() {
        return this.authorRepository.findAuthorByCountOfBook();
    }

    @Override
    public Author findAuthorByFirstAndLastName(String firstName, String lastName) {
        return authorRepository.findByFirstNameAndLastName(firstName,lastName);
    }

//    @Override
//    public List<Author> findAuthorsWithAtLeastOneBookBefore1990() {
//        String date = "1/1/1990";
//       LocalDate formattedDate = this.formatDate(date);
//        return this.authorRepository.findAuthorsByBooksBeforeReleaseDate(formattedDate);
//    }

    private LocalDate formatDate(String date){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate formattedDate = LocalDate.parse(date,dateTimeFormatter);
        return formattedDate;
    }
}

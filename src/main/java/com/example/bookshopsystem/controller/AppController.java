package com.example.bookshopsystem.controller;

import com.example.bookshopsystem.services.AuthorService;
import com.example.bookshopsystem.services.BookService;
import com.example.bookshopsystem.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.util.Scanner;

@Controller
public class AppController implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;
    private Scanner scanner;
    @Autowired
    public AppController(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.categoryService.seedCategories();
        this.authorService.seedAuthors();
        this.bookService.seedBooks();

        //ex1
//        bookService.getAllBooksAfter2000().
//                forEach(book -> System.out.println(book.getTitle()));
        //ex.2

//        List<Author>authors= (List<Author>) this.bookService.getAllAuthorsWithBooksAfter1990().stream()
//            .map(Book::getAuthor).collect(Collectors.toList());
//              authors.forEach(author -> System.out.println(String.format("%s %s %d",
//                      author.getFirstName(),author.getLastName(), author.getBooks().size())));
        //ex.3
//        this.authorService.findAllAuthorsByBookSize().forEach(author -> {
//            System.out.printf("%s %s %d%n", author.getFirstName(), author.getLastName(), author.getBooks().size());
//        });

        //ex.4
//        this.bookService.getAllBooksFromAuthorOrderByReleaseDate()
//                .stream().forEach(book -> {
//            System.out.println(String.format("%s %s %d",
//                    book.getTitle(),book.getReleaseDate().toString(),book.getCopies()));
//        });

        //ex 1
        scanner = new Scanner(System.in);
//        String ageRestriction = scanner.nextLine();
//        this.bookService.getAllBooksWithAgeRestriction(ageRestriction)
//                .forEach(b-> System.out.println(b));
        //ex.2

//        this.bookService.getAllBooksGoldenEditionCopiesLessThan5000()
//                .forEach(System.out::println);

        this.bookService.getAllBooksWithPriceLower5Bigger40().forEach(System.out::println);
    }
}

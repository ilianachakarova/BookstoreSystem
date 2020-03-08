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

        scanner = new Scanner(System.in);
        System.out.println("Please pick a number from 1 to 13(corresponding to the problem number) " +
                "in order to test the problems:");
        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice){
            case 1:
                //ex 1 - Books Titles By Age Restriction
                this.getBooksByAgeRestriction(scanner);
                break;
            case 2:
                //ex.2 - Golden books
                this.getBooksByGoldEdition();
                break;
            case 3:
                //ex3 - Books by Price
                this.getBooksByPriceRestrictions();
                break;
            case 4:
                //ex4 - Not released Books
                this.getBooksWhichAreNotReleasedIn(scanner);
                break;
            case 5:
                //ex5 - Books Released Before Date
                this.getBooksReleasedBefore(scanner);
                break;
            case 6:
                //ex6 - Authors Search
                this.searchAuthorByString(scanner);
                break;
            case 7:
        //ex.7 - Book search
        this.searchBookByString(scanner);
                break;
            case 8:
        //ex.8 - Book Titles Search
        this.searchBookByTitle(scanner);
                break;
            case 9:
        //ex9 - Count Books
        this.getCountBooks(scanner);
                break;
            case 10:

        //ex10 - Total Book Copies
        this.getTotalBookCopies();
                break;
            case 11:
        //11 - reduced book
        this.getReducedBookInfo(scanner);
                break;
            case 12:
        //12 - increase book copies
        this.increaseBookCopies(scanner);
                break;
            case 13:
        //13 - Remove books
        this.removeBooks(scanner);
                break;

        }


    }

    private void removeBooks(Scanner scanner) {
        int minCopies = Integer.parseInt(scanner.nextLine());
        System.out.println(this.bookService.removeBooksWithCopiesLessThan(minCopies));
    }

    private void getReducedBookInfo(Scanner scanner) {
        String title = scanner.nextLine();
        System.out.println(this.bookService.printInfoReducedBook(title));
    }

    private void getBooksByAgeRestriction(Scanner scanner) {
        String ageRestriction = scanner.nextLine();
        this.bookService.getAllBooksWithAgeRestriction(ageRestriction)
                .forEach(System.out::println);
    }

    private void getBooksByGoldEdition() {
        this.bookService.getAllBooksGoldenEditionCopiesLessThan5000()
                .forEach(System.out::println);
    }

    private void getBooksByPriceRestrictions() {
        this.bookService.getAllBooksWithPriceLower5Bigger40().forEach(System.out::println);
    }

    private void getBooksWhichAreNotReleasedIn(Scanner scanner) {
        String year = scanner.nextLine();
        this.bookService.getAllBooksNotReleasedInYear(year).forEach(System.out::println);
    }

    private void getBooksReleasedBefore(Scanner scanner) {
        String date = scanner.nextLine();
        this.bookService.getAllBooksReleasedBeforeDate(date).forEach(System.out::println);
    }

    private void searchAuthorByString(Scanner scanner) {
        String chars = scanner.nextLine();
        this.authorService.findAuthorNamesEndingWith(chars).forEach(System.out::println);
    }

    private void searchBookByString(Scanner scanner) {
        String chars = scanner.nextLine();
        this.bookService.getAllBooksContaining(chars).forEach(System.out::println);
    }

    private void searchBookByTitle(Scanner scanner) {
        String chars = scanner.nextLine();
        this.bookService.getAllBooksByAuthorLastNameStartingWith(chars).forEach(System.out::println);
    }

    private void increaseBookCopies(Scanner scanner) {
        String date = scanner.nextLine();
        int copies = Integer.parseInt(scanner.nextLine());
        int totalCopies = this.bookService.updateBookCopiesAfterDate(date, copies) * copies;

        System.out.println(totalCopies);
    }

    private void getCountBooks(Scanner scanner) {
        int length = Integer.parseInt(scanner.nextLine());
        System.out.println(this.bookService.getAllBooksWhoseTitleIsLongerThan(length));
    }

    private void getTotalBookCopies() {
        this.authorService.findTotalBookCopiesPerAuthor().forEach(System.out::println);
    }
}

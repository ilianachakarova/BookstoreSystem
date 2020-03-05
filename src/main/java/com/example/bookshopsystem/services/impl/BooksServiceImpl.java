package com.example.bookshopsystem.services.impl;

import com.example.bookshopsystem.constants.GlobalConstants;
import com.example.bookshopsystem.entities.*;
import com.example.bookshopsystem.repositories.BookRepository;
import com.example.bookshopsystem.services.AuthorService;
import com.example.bookshopsystem.services.BookService;
import com.example.bookshopsystem.services.CategoryService;
import com.example.bookshopsystem.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class BooksServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final FileUtil fileUtil;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    @Autowired
    public BooksServiceImpl(BookRepository bookRepository, FileUtil fileUtil, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.fileUtil = fileUtil;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedBooks() throws IOException {

        if (this.bookRepository.count() != 0) {
            return;
        }
        String[] fileContent =
                fileUtil.readFileContent(GlobalConstants.BOOKS_FILE_PATH);

        Arrays.stream(fileContent).forEach(r -> {
            String[] tokens = r.split("\\s");

            //get random author
            Author author = this.getRandomAuthor();
            EditionType editionType = EditionType.values()[Integer.parseInt(tokens[0])];
            LocalDate releaseDate = this.formatDate(tokens[1]);
            int copies = Integer.parseInt(tokens[2]);
            BigDecimal price = new BigDecimal(tokens[3]);
            AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(tokens[4])];

            String title = this.getTitle(tokens);
            Set<Category> categories = this.getRandomCategories();

            Book book = new Book();
            book.setAuthor(author);
            book.setEditionType(editionType);
            book.setReleaseDate(releaseDate);
            book.setCopies(copies);
            book.setPrice(price);
            book.setAgeRestriction(ageRestriction);
            book.setTitle(title);
            book.setCategories(categories);

            this.bookRepository.saveAndFlush(book);

        });
    }

    @Override
    public List<Book> getAllBooksAfter2000() {
        LocalDate localDate = this.formatDate("31/12/2000");
        return this.bookRepository.findAllByReleaseDateAfter(localDate);
    }

    @Override
    public List<Book> getAllBooksFromAuthorOrderByReleaseDate() {
        Author author1 = this.authorService.findAuthorByFirstAndLastName("George", "Powell");
        return this.bookRepository.findAllByAuthorOrderByReleaseDateDescTitle(author1);
    }

    @Override
    public List<Book> getAllAuthorsWithBooksAfter1990() {
        String date = "1/1/1990";
        LocalDate formatted = this.formatDate(date);
        return bookRepository.getAllAuthorsWithAtLeastOneBookAfter1990(formatted);
    }

    @Override
    public List<String> getAllBooksWithAgeRestriction(String ageRestriction) {
        AgeRestriction ageRestriction1 = AgeRestriction.valueOf(ageRestriction.toUpperCase());
        List<Book> books = this.bookRepository.findAllByAgeRestrictionEquals(ageRestriction1);
        return books.stream()
                .map(book -> String.format("%s", book.getTitle())).collect(Collectors.toList());
    }

    @Override
    public List<String> getAllBooksGoldenEditionCopiesLessThan5000() {
        List<Book> books = this.bookRepository.findAllByEditionTypeEqualsAndCopiesBefore(EditionType.valueOf("GOLD"),5000);
        return books.stream()
                .map(Book::getTitle).collect(Collectors.toList());
    }

    private Set<Category> getRandomCategories() {
        Set<Category> categories = new LinkedHashSet<>();

        Random random = new Random();
        int bound = random.nextInt(3) + 1;
        for (int i = 0; i < bound; i++) {
            int categoryId = random.nextInt(8) + 1;
            categories.add(categoryService.findCategoryById(categoryId));
        }
        return categories;
    }

    private String getTitle(String[] tokens) {
        StringBuilder sb = new StringBuilder();
        for (int i = 5; i < tokens.length; i++) {
            sb.append(tokens[i]).append(" ");
        }

        return sb.toString().trim();
    }

    private Author getRandomAuthor() {
        Random random = new Random();

        int randomId = random.nextInt(authorService.getAllAuthorsCount()) + 1;

        Author randomAuthor = this.authorService.findAuthorById(randomId);

        return randomAuthor;
    }

    private LocalDate formatDate(String date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate formattedDate = LocalDate.parse(date, dateTimeFormatter);
        return formattedDate;
    }
}

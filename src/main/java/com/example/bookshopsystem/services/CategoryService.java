package com.example.bookshopsystem.services;

import com.example.bookshopsystem.entities.Category;

import java.io.IOException;

public interface CategoryService {
    void seedCategories() throws IOException;
    Category findCategoryById(long id);
}

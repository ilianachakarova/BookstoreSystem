package com.example.bookshopsystem.services.impl;

import com.example.bookshopsystem.constants.GlobalConstants;
import com.example.bookshopsystem.entities.Category;
import com.example.bookshopsystem.repositories.CategoryRepository;
import com.example.bookshopsystem.services.CategoryService;
import com.example.bookshopsystem.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, FileUtil fileUtil) {
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
    }


    @Override
    public void seedCategories() throws IOException {

        if(this.categoryRepository.count() !=0){
            return;
        }

        String [] fileContent =
                this.fileUtil.readFileContent(GlobalConstants.CATEGORIES_FILE_PATH);

        Arrays.stream(fileContent)
                .forEach(r->{
                    Category category = new Category(r);
                    this.categoryRepository.saveAndFlush(category);
                });


    }

    @Override
    public Category findCategoryById(long id) {
      return this.categoryRepository.getOne(id);
    }
}

package com.example.raf_news_backend.services;

import com.example.raf_news_backend.entities.Category;
import com.example.raf_news_backend.repositories.categories.ICategoryRepository;

import javax.inject.Inject;
import java.util.List;

public class CategoryService {
    @Inject
    private ICategoryRepository categoryRepository;

    public Category addCategory(Category category) {
        return this.categoryRepository.addCategory(category);
    }

    public List<Category> allCategories() {
        return this.categoryRepository.allCategories();
    }

//    public Category findCategory(Integer id) {
//        return this.categoryRepository.findCategory(id);
//    }

    public Category findCategory(String name) {
        return this.categoryRepository.findCategory(name);
    }

//    public void deleteCategory(Integer id) {
//        this.categoryRepository.deleteCategory(id);
//    }

    public void deleteCategory(String name) {
        this.categoryRepository.deleteCategory(name);
    }

    public void updateCategory(String oldName, String newName, String newDescription) {
        this.categoryRepository.updateCategory(oldName, newName, newDescription);
    }
}

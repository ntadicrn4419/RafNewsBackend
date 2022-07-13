package com.example.raf_news_backend.repositories.categories;

import com.example.raf_news_backend.entities.Category;

import java.util.List;

public interface ICategoryRepository {
    Category addCategory(Category category);
    List<Category> allCategories();
    Category findCategory(String name);
    void deleteCategory(String name);
    void updateCategory(String oldName, String newName, String newDescription);
}

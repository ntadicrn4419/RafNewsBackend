package com.example.raf_news_backend.repositories.categories;

import com.example.raf_news_backend.entities.Category;

import java.util.List;

public interface ICategoryRepository {
    public Category addCategory(Category category);
    public List<Category> allCategories();
    //public Category findCategory(Integer id);
    public Category findCategory(String name);
    //public void deleteCategory(Integer id);
    public void deleteCategory(String name);

    public void updateCategory(String oldName, String newName, String newDescription);
}

package com.example.raf_news_backend.repositories.categories;

import com.example.raf_news_backend.entities.Category;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryCategoryRepository implements ICategoryRepository{

    public static List<Category> categoryList = new CopyOnWriteArrayList<>();

    static {
        Category c1 = new Category();
        c1.setId(1);
        c1.setName("Sport");
        c1.setDescription("Kategorija o sportskim dogadjajima");

        Category c2 = new Category();
        c2.setId(2);
        c2.setName("Politika");
        c2.setDescription("Kategorija sa vestima iz politike.");

        Category c3 = new Category();
        c3.setId(3);
        c3.setName("Svet");
        c3.setDescription("Kategorija sa vestima iz svih delova sveta");

        categoryList.add(c1);
        categoryList.add(c2);
        categoryList.add(c3);
    }

    @Override
    public Category addCategory(Category category) {

        int id = categoryList.size();
        category.setId(id+1);
        categoryList.add(Math.toIntExact(id), category);
        return category;
    }

    @Override
    public List<Category> allCategories() {
        return new ArrayList<>(categoryList);
    }

//    @Override
//    public Category findCategory(Integer id) {
//        for(Category c: categoryList){
//            if(c.getId().equals(id)){
//                return c;
//            }
//        }
//        return null;
//    }

    @Override
    public Category findCategory(String name) {
        for(Category c: categoryList){
            if(c.getName().equals(name)){
                return c;
            }
        }
        return null;
    }

//    @Override
//    public void deleteCategory(Integer id) {
//        categoryList.removeIf(c -> c.getId().equals(id));
//    }

    @Override
    public void deleteCategory(String name) {
        categoryList.removeIf(c -> c.getName().equals(name));
    }

    @Override
    public void updateCategory(String oldName, String newName, String newDescription) {
        for(Category c: categoryList){
            if(c.getName().equals(oldName)){
                c.setName(newName);
                c.setDescription(newDescription);
            }
        }
    }
}

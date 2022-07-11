package com.example.raf_news_backend.repositories.news;

import com.example.raf_news_backend.entities.Comment;
import com.example.raf_news_backend.entities.News;

import java.util.List;

public interface INewsRepository {

    News addNews(News news);
    List<News> allNews();
    News findNews(Integer id);
    void deleteNews(Integer id);
    List<News> findAllNewsByCategoryName(String categoryName);
    void updateNews(Integer id, String title, String category, String tags, String text);
    void updateCategoryName(String oldCategoryName, String newCategoryName);
    Comment addComment(Integer newsId, Comment comment);
}

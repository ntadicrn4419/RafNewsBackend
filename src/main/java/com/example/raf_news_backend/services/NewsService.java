package com.example.raf_news_backend.services;

import com.example.raf_news_backend.entities.Comment;
import com.example.raf_news_backend.entities.News;
import com.example.raf_news_backend.repositories.news.INewsRepository;

import javax.inject.Inject;
import java.util.List;

public class NewsService {
    @Inject
    private INewsRepository newsRepository;

    public News addNews(News news) {
        return this.newsRepository.addNews(news);
    }

    public List<News> allNews() {
        return this.newsRepository.allNews();
    }

    public News findNews(Integer id) {
        return this.newsRepository.findNews(id);
    }

    public void deleteNews(Integer id) {
        this.newsRepository.deleteNews(id);
    }

    public List<News> allNewsbyCategory(String categoryName) {
        return this.newsRepository.findAllNewsByCategoryName(categoryName);
    }

    public void updateNews(Integer id, String title, String category, String tags, String text) {
        this.newsRepository.updateNews(id, title, category, tags, text);
    }

    public void updateCategoryName(String oldCategoryName, String newCategoryName) {
        this.newsRepository.updateCategoryName(oldCategoryName, newCategoryName);
    }

    public Comment addComment(Integer newsId, Comment comment) {
        return this.newsRepository.addComment(newsId, comment);
    }

    public List<News> allSimilarNewsByKeyword(String keyword) {
        return this.newsRepository.findAllSimilarNewsByKeyword(keyword);
    }
}

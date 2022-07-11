package com.example.raf_news_backend.resources;


import com.example.raf_news_backend.entities.Comment;
import com.example.raf_news_backend.entities.News;
import com.example.raf_news_backend.services.NewsService;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/news")
public class NewsResource {
    @Inject
    private NewsService newsService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> all()
    {
        return this.newsService.allNews();
    }

    @GET
    @Path("/{categoryName}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> allByCategory(@PathParam("categoryName") String categoryName)
    {
        return this.newsService.allNewsbyCategory(categoryName);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public News create(@Valid News news) {
        System.out.println("Usao je u create u NewsResource");
        return this.newsService.addNews(news);
    }

    @POST
    @Path("/addComment/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Comment addComment(@PathParam("id") Integer newsId, @Valid Comment comment) {
        return this.newsService.addComment(newsId, comment);
    }

    @GET
    @Path("/find/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public News find(@PathParam("id") Integer id) {
        return this.newsService.findNews(id);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Integer id) {
        this.newsService.deleteNews(id);
    }

    @PUT
    @Path("/{id}")
    public void update(@PathParam("id") Integer id, @Valid News news){
        this.newsService.updateNews(id, news.getTitle(), news.getCategory(), news.getTags(), news.getText());
    }
    @PUT
    @Path("/update-category-name/{oldCategoryName}/{newCategoryName}")
    public void updateCategoryName(@PathParam("oldCategoryName") String oldCategoryName, @PathParam("newCategoryName") String newCategoryName){
        this.newsService.updateCategoryName(oldCategoryName, newCategoryName);
    }

}

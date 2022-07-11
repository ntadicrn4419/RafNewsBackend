package com.example.raf_news_backend.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class News {

    private Integer id;

    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private String title;

    @NotNull(message = "Text field is required")
    @NotEmpty(message = "Text field is required")
    private String text;

    @NotNull(message = "Tags field is required")
    @NotEmpty(message = "Tags field is required")
    private String tags;

    @NotNull(message = "Category field is required")
    @NotEmpty(message = "Category field is required")
    private String category;

    private String dateCreated;

    private Integer numberOfVisits;

    private User author;

    private List<Comment> comments;

    private List<Tag> tagList;



    public News() {

    }

    public News(Integer id, String title, String text, String dateCreated, Integer numberOfVisits, User author, List<Comment> comments, String tags, String category) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.dateCreated = dateCreated;
        this.numberOfVisits = numberOfVisits;
        this.author = author;
        this.comments = comments;
        this.tags = tags;
        this.category = category;
    }

    public News(String title, String category, String tags, String text){
        this.title = title;
        this.category = category;
        this.tags = tags;
        this.text = text;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Integer getNumberOfVisits() {
        return numberOfVisits;
    }

    public void setNumberOfVisits(Integer numberOfVisits) {
        this.numberOfVisits = numberOfVisits;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}

package com.example.raf_news_backend.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class Comment {

    private Integer id;

    @NotNull(message = "Author field is required")
    @NotEmpty(message = "Author field is required")
    private String author;

    @NotNull(message = "Text field is required")
    @NotEmpty(message = "Text field is required")
    private String text;

//    @NotNull(message = "Date created field is required")
//    @NotEmpty(message = "Date created field is required")
    private String dateCreated;

    public Comment() {

    }

    public Comment(String author, String text){
        this.author = author;
        this.text = text;
    }

    public Comment(Integer id, String author, String text, String dateCreated) {
        this.id = id;
        this.author = author;
        this.text = text;
        this.dateCreated = dateCreated;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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
}

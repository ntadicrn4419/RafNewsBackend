package com.example.raf_news_backend.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class Tag {

    private Integer id;

    @NotNull(message = "Key words field is required")
    @NotEmpty(message = "Key words field is required")
    private List<String> keyWords;

    public Tag() {

    }

    public Tag(Integer id, List<String> keyWords) {
        this.id = id;
        this.keyWords = keyWords;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<String> getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(List<String> keyWords) {
        this.keyWords = keyWords;
    }
}

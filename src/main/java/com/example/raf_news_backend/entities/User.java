package com.example.raf_news_backend.entities;

import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class User {

    @NotNull(message = "Email field is required")
    @NotEmpty(message = "Email field is required")
    //@UniqueElements //i want email to be unique. check if this is for that
    private String email;

    @NotNull(message = "Firstname field is required")
    @NotEmpty(message = "Firstname field is required")
    private String firstname;

    @NotNull(message = "Lastname field is required")
    @NotEmpty(message = "Lastname field is required")
    private String lastname;

    @NotNull(message = "Type field is required")
    @NotEmpty(message = "Type field is required")
    private String type;// "content_creator" or "admin"

    @NotNull(message = "Active field is required")
    private Boolean active;

    @NotNull(message = "Password field is required")
    @NotEmpty(message = "Password field is required")
    private String password;

    public User(){

    }

    public User(String email, String firstname, String lastname, String type, Boolean active, String password) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.type = type;
        this.active = active;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

package com.example.raf_news_backend.repositories.user;

import com.example.raf_news_backend.entities.User;

import java.util.List;

public interface IUserRepository {
    User findUser(String email);
    List<User> getAll();
    User addUser(User user);
    void updateUser(String oldEmail, String firstname, String lastname, String email, String password, String type, Boolean active);
    boolean activateDeactivateUser(String email);
}

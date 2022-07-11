package com.example.raf_news_backend.repositories.user;

import com.example.raf_news_backend.entities.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryUserRepository implements IUserRepository{

    public static List<User> USERS = new CopyOnWriteArrayList<>();

    static {
        USERS.add(new User("admin@gmail.com", "Nikola", "Nikolic", "admin", true, "a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3")); // 123
        USERS.add(new User("u1@gmail.com", "u1", "u1", "content_creator", true, "2ac9a6746aca543af8dff39894cfe8173afba21eb01c6fae33d52947222855ef")); // 000
    }

    @Override
    public User findUser(String email) {
        User user = null;
        Iterator<User> userIterator = USERS.iterator();
        while (userIterator.hasNext() && user == null) {
            User u = userIterator.next();
            if (u.getEmail().equals(email) && u.getActive()) {
                user = u;
            }
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(USERS);
    }

    @Override
    public User addUser(User user) {
        String tmp = user.getPassword();
        user.setPassword(DigestUtils.sha256Hex(tmp));
        USERS.add(user);
        return user;
    }

    @Override
    public void updateUser(String oldEmail, String firstname, String lastname, String email, String password, String type, Boolean active) {
        for(User u: USERS){
            if(u.getEmail().equals(oldEmail)){
                u.setFirstname(firstname);
                u.setLastname(lastname);
                u.setEmail(email);
                u.setPassword(DigestUtils.sha256Hex(password));
                u.setType(type);
                u.setActive(active);
                return;
            }
        }
    }

    @Override
    public boolean activateDeactivateUser(String email) {
        for(User u: USERS){
            if(u.getEmail().equals(email)){
                u.setActive(!u.getActive());
                return u.getActive();
            }
        }
        return false;
    }
}

package com.example.raf_news_backend.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.raf_news_backend.entities.Category;
import com.example.raf_news_backend.entities.User;
import com.example.raf_news_backend.repositories.user.IUserRepository;
import com.example.raf_news_backend.resources.UserResource;
import org.apache.commons.codec.digest.DigestUtils;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import java.util.Date;
import java.util.List;

public class UserService {
    @Inject
    IUserRepository userRepository;

    public static User currentUser;

    public String login(String email, String password)
    {
        String hashedPassword = DigestUtils.sha256Hex(password);

        User user = this.userRepository.findUser(email);
        if (user == null || !user.getPassword().equals(hashedPassword) || !user.getActive()) {
            return null;
        }

        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + 24*60*60*1000); // One day

        Algorithm algorithm = Algorithm.HMAC256("secret");

        return JWT.create()
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .withSubject(email)
                .withClaim("type", user.getType())
                .sign(algorithm);
    }

    public boolean isAuthorized(String token, ContainerRequestContext req){
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm)
                .build();
        DecodedJWT jwt = verifier.verify(token);

        String email = jwt.getSubject();
        String userType = jwt.getClaim("type").asString();

        User user = this.userRepository.findUser(email);

        if (user == null){
            return false;
        }

        currentUser = user;

        List<Object> matchedResources = req.getUriInfo().getMatchedResources();
        for (Object matchedResource : matchedResources) {
            if (matchedResource instanceof UserResource) {// && req.getMethod().equals("POST") || req.getMethod().equals("PUT")
                if(!userType.equals("admin")){
                    return false;
                }
            }
        }

        return true;
    }

    public List<User> all() {
        return this.userRepository.getAll();
    }

    public User addUser(User user) {
        for(User u: userRepository.getAll()){
            if(u.getEmail().equalsIgnoreCase(user.getEmail())){
                return null;
            }
        }
        return this.userRepository.addUser(user);
    }

    public User findUserByEmail(String email) {
        return this.userRepository.findUser(email);
    }

    public void updateUser(String oldEmail, String firstname, String lastname, String email, String password, String type, Boolean active) {
        this.userRepository.updateUser(oldEmail, firstname, lastname, email, password, type, active);
    }

    public boolean activateDeactivateUser(String email) {
        return this.userRepository.activateDeactivateUser(email);
    }
}

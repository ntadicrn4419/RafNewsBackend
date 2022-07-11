package com.example.raf_news_backend.resources;


import com.example.raf_news_backend.entities.Category;
import com.example.raf_news_backend.entities.User;
import com.example.raf_news_backend.request.LoginRequest;
import com.example.raf_news_backend.services.UserService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/users")
public class UserResource {

    @Inject
    private UserService userService;

    @POST
    @Path("/login")
    @Produces({MediaType.APPLICATION_JSON})
    public Response login(@Valid LoginRequest loginRequest)
    {
        System.out.println("Usao je u login u UserResource");
        Map<String, String> response = new HashMap<>();

        String jwt = this.userService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (jwt == null) {
            response.put("message", "These credentials do not match our records");
            return Response.status(422, "Unprocessable Entity").entity(response).build();
        }

        response.put("jwt", jwt);

        return Response.ok(response).build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<User> all(){
        return this.userService.all();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public User create(@Valid User user) {
        return this.userService.addUser(user);
    }

    @GET
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public User findUserByEmail(@PathParam("email") String email) {
        return this.userService.findUserByEmail(email);
    }

    @PUT
    @Path("/{email}")
    public void updateUser(@PathParam("email") String oldEmail, @Valid User user){
        this.userService.updateUser(oldEmail, user.getFirstname(), user.getLastname(),
                user.getEmail(), user.getPassword(), user.getType(),user.getActive());
    }

    @GET
    @Path("/activate-deactivate/{email}")
    public boolean activateDeactivate(@PathParam("email") String email){
        return this.userService.activateDeactivateUser(email);
    }

}

package com.alankehoe.controllers.api;

import com.alankehoe.persistence.models.User;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class UsersController extends BaseController {

    @RequestMapping(value = "/users", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<User> index() {
        try {
            return getUserService("application").findAll();
        } catch (ConnectionException e) {
            return null;
        }
    }

    @RequestMapping(value = "/users/{ref}", method = RequestMethod.GET, headers = "Accept=application/json")
    public User show(@PathVariable("ref") String ref) {
        try {
            return getUserService("application").findByRef(UUID.fromString(ref));
        } catch (ConnectionException e) {
            return null;
        }
    }

    @RequestMapping(value = "/users/create", method = RequestMethod.GET, headers = "Accept=application/json")
    public User create() {
        try {
            return getUserService("application").create(mockUser());
        } catch (ConnectionException e) {
            return null;
        }
    }

    private User mockUser() {
        User user = new User();
        user.setName("alan kehoe");
        user.setEmail("alankehoe111@gmail.com");
        user.setPassword("password");
        return user;
    }
}

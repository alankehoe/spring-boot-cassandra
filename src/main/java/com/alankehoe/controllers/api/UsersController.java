package com.alankehoe.controllers.api;

import com.alankehoe.persistence.models.User;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UsersController extends BaseController {

    @RequestMapping(value = "/users", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<User> index() {
        return getUserService("application").list();
    }

    @RequestMapping(value = "/users/new", method = RequestMethod.GET, headers = "Accept=application/json")
    public User show() {
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

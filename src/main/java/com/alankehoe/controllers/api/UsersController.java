package com.alankehoe.controllers.api;

import com.alankehoe.models.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class UsersController extends BaseController {

    @RequestMapping(value = "/users", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<User> index() {
        return userService.list();
    }

    @RequestMapping(value = "/users/new", method = RequestMethod.GET, headers = "Accept=application/json")
    public User show() {
        return userService.create(mockUser());
    }

    private User mockUser() {
        User user = new User();
        user.setRef(UUID.randomUUID());
        user.setFirstName("alan");
        user.setLastName("kehoe");
        user.setAge(23);
        user.setEmail("alankehoe111@gmail.com");
        return user;
    }
}

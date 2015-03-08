package com.alankehoe.controllers.api;

import com.alankehoe.models.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UsersController extends BaseController {

    @RequestMapping(value = "/users", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<User> index() {
        return userService.listUsers();
    }
}

package com.alankehoe.controllers.api;

import com.alankehoe.controllers.ApplicationController;
import com.alankehoe.models.User;
import com.alankehoe.services.PersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BaseController extends ApplicationController {

    @Autowired
    protected PersistenceService<User> userService;
}

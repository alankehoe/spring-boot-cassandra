package com.alankehoe.services.impl;

import com.alankehoe.models.User;
import com.alankehoe.services.UserService;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public List<User> listUsers() {
        List<User> users = Lists.newArrayList();
        IntStream.range(0, 100).forEach((i -> users.add(mockUser())));
        return users;
    }

    private User mockUser() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setFirstName("alan");
        user.setLastName("kehoe");
        user.setAge(23);
        return user;
    }
}

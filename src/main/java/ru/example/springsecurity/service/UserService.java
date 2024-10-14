package ru.example.springsecurity.service;

import ru.example.springsecurity.models.User;

import java.util.List;

public interface UserService {
    List<User> getAllUser();

    User getUserById(Long id);

    void saveUser(User existingUser, User user);

    void deleteUser(User user);

    User getUserByFirstname(String name);
}

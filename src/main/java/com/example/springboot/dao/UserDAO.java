package com.example.springboot.dao;

import com.example.springboot.entity.User;

import java.util.List;

public interface UserDAO {
    List<User> getAllUsers();

    void addUser(User user);

    User getUser(int id);

    void updateUser(User user);

    void deleteUser(User user);
}

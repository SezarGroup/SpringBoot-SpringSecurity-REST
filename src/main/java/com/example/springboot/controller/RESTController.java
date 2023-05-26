package com.example.springboot.controller;

import com.example.springboot.entity.User;
import com.example.springboot.exception_handling.NoSuchUserException;
import com.example.springboot.exception_handling.UserIncorrectData;
import com.example.springboot.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RESTController {

    private final UserService service;

    public RESTController(UserService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> showAllUser() {
        return service.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable int id) {
        User user = service.getUser(id);
        if (user == null) {
            throw new NoSuchUserException("There user with ID = " + id + " in DB");
        }
        return user;
    }

    @PostMapping("/users")
    public User addNewUser(@RequestBody User user) {
        service.addUser(user);
        return user;
    }

    @PutMapping("/users")
    public User editUser(@RequestBody User user) {
        service.updateUser(user);
        return user;
    }

    @DeleteMapping("users/{id}")
    public String deleteUser(@PathVariable int id) {
        User user = service.getUser(id);
        if (user == null) {
            throw new NoSuchUserException("There user with ID = " + id + " in DB");
        }
        service.deleteUser(user);
        return "User with ID= " + id + " was deleted";
    }

    @ExceptionHandler
    public ResponseEntity<UserIncorrectData> handleException(NoSuchUserException noSuchUserException) {
        UserIncorrectData incorrectData = new UserIncorrectData();
        incorrectData.setInfo(noSuchUserException.getMessage());
        return new ResponseEntity<>(incorrectData, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<UserIncorrectData> handleException(Exception noSuchUserException) {
        UserIncorrectData incorrectData = new UserIncorrectData();
        incorrectData.setInfo(noSuchUserException.getMessage());
        return new ResponseEntity<>(incorrectData, HttpStatus.BAD_REQUEST);
    }
}

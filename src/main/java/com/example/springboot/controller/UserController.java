package com.example.springboot.controller;

import com.example.springboot.entity.User;
import com.example.springboot.security.PersonDetails;
import com.example.springboot.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

//@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping( "/users")
    public String showAllUsers(Model model) {
        List<User> allUsers = userService.getAllUsers();
        model.addAttribute("users", allUsers);
        User user = new User();
        model.addAttribute("user", user);
        return "users";
    }

    @RequestMapping("/user-info")
    public String showUserInfo(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        User user = personDetails.getUser();
        model.addAttribute("user", user);
        return "user-page";
    }

    @RequestMapping("/user-page")
    public String showUserPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        User user = personDetails.getUser();
        model.addAttribute("user", user);
        return "user-page";
    }

    @RequestMapping("/addNewUser")
    public String addNewUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "user-info";
    }

    @RequestMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/users";
    }

    @RequestMapping("/editUser")
    public String editUser(@RequestParam(required = false) String id, Model model) {
        int userId = Integer.parseInt(id);
        User user = userService.getUser(userId);
        model.addAttribute("user", user);
        return "user-edit";
    }

    @RequestMapping("/updateUser")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/users";
    }

    @RequestMapping("/deleteUser")
    public String deleteUser(@RequestParam(required = false) String id) {
        int userId = Integer.parseInt(id);
        User user = userService.getUser(userId);
        userService.deleteUser(user);
        return "redirect:/users";
    }
}

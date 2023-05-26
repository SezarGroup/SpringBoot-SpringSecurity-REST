package com.example.springboot.util;

import com.example.springboot.entity.User;
import com.example.springboot.service.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    @Autowired
    private PersonDetailsService personDetailsService;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        try {
            personDetailsService.loadUserByUsername(user.getFirstName());
        } catch (UsernameNotFoundException e) {
            return;
        }

        errors.rejectValue("username", "", "User уже существует");
    }
}

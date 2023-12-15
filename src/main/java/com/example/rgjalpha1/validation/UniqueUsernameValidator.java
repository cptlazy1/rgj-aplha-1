package com.example.rgjalpha1.validation;

import com.example.rgjalpha1.exception.UsernameExistsException;
import com.example.rgjalpha1.repository.UserRepository;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    @Autowired
    private UserRepository userRepository;

    // Todo: enforce unique username
    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (username != null && !userRepository.existsByUsername(username)) {
            throw new UsernameExistsException("Username already exists. Please choose another one");
        }

        return true;
    }
}
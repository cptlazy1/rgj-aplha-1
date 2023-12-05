package com.example.rgjalpha1.exception;

import java.io.Serial;

public class UsernameNotFoundException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public UsernameNotFoundException() {
        super();
    }

    public UsernameNotFoundException(String username) {
        super("User with username " + username + " not found");
    }
}

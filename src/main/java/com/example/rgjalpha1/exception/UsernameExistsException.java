package com.example.rgjalpha1.exception;

import java.io.Serial;


public class UsernameExistsException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

        public UsernameExistsException(String message) {
            super(message);
        }
}

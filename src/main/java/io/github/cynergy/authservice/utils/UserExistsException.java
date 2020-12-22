package io.github.cynergy.authservice.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "User already exists.")
public class UserExistsException extends Exception {
    public UserExistsException() {
        super("User already exists.");
    }
}

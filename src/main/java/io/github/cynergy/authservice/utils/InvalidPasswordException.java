package io.github.cynergy.authservice.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Invalid password.")
public class InvalidPasswordException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public InvalidPasswordException() {
        super("Invalid password");
    }
}

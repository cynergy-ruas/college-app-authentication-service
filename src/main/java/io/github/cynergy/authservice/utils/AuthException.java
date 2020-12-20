package io.github.cynergy.authservice.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error creating token.")
public class AuthException extends Exception{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public AuthException(String message) {
        super(message);
    }
    
}

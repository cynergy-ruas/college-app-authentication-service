package io.github.cynergy.authservice.service;

import io.github.cynergy.authservice.model.User;
import io.github.cynergy.authservice.utils.AuthException;

public interface TokenGenerationService {

    /**
     * Generates the token for a user.
     * @param user
     * @return the token.
     */
    public String generateToken(User user) throws AuthException;
}

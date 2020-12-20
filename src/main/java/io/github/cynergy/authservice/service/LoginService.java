package io.github.cynergy.authservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.cynergy.authservice.database.UserDao;
import io.github.cynergy.authservice.model.User;
import io.github.cynergy.authservice.utils.AuthException;
import io.github.cynergy.authservice.utils.InvalidPasswordException;
import io.github.cynergy.authservice.utils.UserNotFoundException;

@Service
public class LoginService {
    UserDao dao;
    TokenGenerationService tokenService;
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public LoginService(@Qualifier("mongo-legacy") UserDao dao, @Qualifier("firebase") TokenGenerationService tokenService) {
        this.dao = dao;
        this.tokenService = tokenService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public String login(User user) throws AuthException, InvalidPasswordException, UserNotFoundException {
        // getting the password from the request
        String givenPassword = user.getPassword();

        // getting the user entry from the database
        user = this.dao.getUser(user.getRollNumber());

        // checking if password matches
        boolean isMatch = this.passwordEncoder.matches(givenPassword, user.getPassword());

        // generating and returning token if password matches
        if (isMatch) {
            return this.tokenService.generateToken(user);
        }
        else {
            throw new InvalidPasswordException();
        }
    }
}

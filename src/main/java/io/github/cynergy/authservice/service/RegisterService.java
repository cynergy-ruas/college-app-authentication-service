package io.github.cynergy.authservice.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.cynergy.authservice.database.UserDao;
import io.github.cynergy.authservice.model.User;
import io.github.cynergy.authservice.utils.AuthException;
import io.github.cynergy.authservice.utils.UserNotFoundException;
import io.github.cynergy.authservice.utils.UserExistsException;

@Service
public class RegisterService {
    UserDao dao;
    TokenGenerationService tokenService;
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public RegisterService(@Qualifier("mongo-legacy") UserDao dao, @Qualifier("firebase") TokenGenerationService tokenService) {
        this.dao = dao;
        this.tokenService = tokenService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public String register(User user) throws AuthException, UserExistsException {

        try {
            this.dao.getUser(user.getRollNumber());
            
            // since the get user operation succeded without error, that means a user with the given roll
            // number already exists, so throwing an error
            throw new UserExistsException();

        } catch (UserNotFoundException e) {
            // hashing the password
            user.setPassword(this.passwordEncoder.encode(user.getPassword()));

            // adding the user to the database
            String userId = this.dao.addUser(user);

            // adding the user id to the usr
            user.setUid(userId);

            // generating token
            String token = tokenService.generateToken(user);

            // returning token
            return token;
        }
    }
}

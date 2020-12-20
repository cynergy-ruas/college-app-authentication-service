package io.github.cynergy.authservice.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.cynergy.authservice.database.UserDao;
import io.github.cynergy.authservice.model.User;
import io.github.cynergy.authservice.utils.AuthException;

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

    public String register(User user) throws AuthException {
        // generating a user id for the user
        user.setUid(generateUserId());

        // hashing the password
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        // adding the user to the database
        this.dao.addUser(user);

        // generating token
        String token = tokenService.generateToken(user);

        // returning token
        return token;
    }

    private String generateUserId() {
        return UUID.randomUUID().toString();
    }
}

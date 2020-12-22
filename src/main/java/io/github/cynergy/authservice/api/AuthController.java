package io.github.cynergy.authservice.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.cynergy.authservice.model.TokenResponse;
import io.github.cynergy.authservice.model.User;
import io.github.cynergy.authservice.service.LoginService;
import io.github.cynergy.authservice.service.RegisterService;
import io.github.cynergy.authservice.utils.AuthException;
import io.github.cynergy.authservice.utils.InvalidPasswordException;
import io.github.cynergy.authservice.utils.UserNotFoundException;
import io.github.cynergy.authservice.utils.UserExistsException;

@RequestMapping("auth")
@RestController
public class AuthController {
    RegisterService registerService;
    LoginService loginService;

    @Autowired
    AuthController(RegisterService registerService, LoginService loginService) {
        this.registerService = registerService;
        this.loginService = loginService;
    }

    @PostMapping("register")
    TokenResponse register(@RequestBody User user) throws AuthException, UserExistsException {
        String token = this.registerService.register(user);
        return new TokenResponse(token);
    }

    @PostMapping("login")
    TokenResponse login(@RequestBody User user) throws AuthException, InvalidPasswordException, UserNotFoundException {
        String token = this.loginService.login(user);
        return new TokenResponse(token);
    }
}

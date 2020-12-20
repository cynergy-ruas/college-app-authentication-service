package io.github.cynergy.authservice.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import org.springframework.stereotype.Service;

import io.github.cynergy.authservice.model.User;
import io.github.cynergy.authservice.utils.AuthException;

@Service("firebase")
public class FirebaseTokenGenerationService implements TokenGenerationService {

    public FirebaseTokenGenerationService() throws IOException {
        try {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.getApplicationDefault()).build();
            FirebaseApp.initializeApp(options);
        } catch (IllegalStateException e) {
            // firebase app has already been initialized, so dont do anything.
        }
    }

    @Override
    public String generateToken(User user) throws AuthException{
        // creating the custom claims
        Map<String, Object> customClaims = new HashMap<String, Object>();
        customClaims.put("clearance", user.getClearance());

        // creating the token
        String token = null;
        try {
            token = FirebaseAuth.getInstance().createCustomToken(user.getUid(), customClaims);
        } catch (FirebaseAuthException e) {
            throw new AuthException(String.format("Error creating token: %s", e.getMessage()));
        }

        return token;
    }
}

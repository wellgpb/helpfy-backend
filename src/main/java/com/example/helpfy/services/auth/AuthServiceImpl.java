package com.example.helpfy.services.auth;

import com.example.helpfy.dtos.auth.LoginRequest;
import com.example.helpfy.dtos.auth.LoginResponse;
import com.example.helpfy.dtos.user.UserMapper;
import com.example.helpfy.exceptions.BadRequestException;
import com.example.helpfy.exceptions.Constants;
import com.example.helpfy.exceptions.NotFoundException;
import com.example.helpfy.exceptions.UnauthorizedException;
import com.example.helpfy.models.User;
import com.example.helpfy.repositories.UserRepository;
import com.example.helpfy.services.encoder.Encoder;
import com.example.helpfy.utils.JwtUtils;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final Encoder encoder;
    private final UserMapper userMapper;

    @Value("${google.clientId}")
    private String clientId;

    public AuthServiceImpl(UserRepository userRepository, JwtUtils jwtUtils, Encoder encoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.encoder = encoder;
        this.userMapper = userMapper;
    }

    @Override
    public LoginResponse auth(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> {
            throw new NotFoundException(Constants.EMAIL_NOT_FOUND);
        });

        boolean validPassword = encoder.matches(loginRequest.getPassword(), user.getPassword());
        if (!validPassword) {
            throw new BadRequestException(Constants.INVALID_PASSWORD);
        }

        var userResponse = userMapper.fromUserToResponse(user);
        String token = jwtUtils.generateToken(user.getEmail());
        return new LoginResponse(token, userResponse);
    }

    // um crime de código foi cometido nesse método. mas por questão de estar puto, ficará assim.
    @Transactional
    @Override
    public String validateToken(String token) {
        HttpTransport httpTransport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(httpTransport, jsonFactory)
                .setAudience(Collections.singletonList(clientId))
                .build();

        GoogleIdToken idToken = null;
        try {
            idToken = verifier.verify(token);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (idToken != null) {
            Payload payload = idToken.getPayload();

            String email = payload.getEmail();
            String avatarLink = (String) payload.get("picture");
            String lastName = (String) payload.get("family_name");
            String name = (String) payload.get("given_name");

            if (!(email.endsWith("@ccc.ufcg.edu.br") || email.endsWith("@computacao.ufcg.edu.br"))) {
                throw new BadRequestException("O email não faz parte do curso CC@UFCG");
            }

            Optional<User> optionalUser = userRepository.findByEmail(email);
            if (!optionalUser.isPresent()) {
                User userToSave = new User();
                userToSave.setEmail(email);
                userToSave.setName(name);
                userToSave.setLastName(lastName);
                userToSave.setAvatarLink(avatarLink);

                userRepository.save(userToSave);
            }

            return email;
        } else {
            throw new UnauthorizedException();
        }
    }
}

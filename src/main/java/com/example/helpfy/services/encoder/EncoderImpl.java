package com.example.helpfy.services.encoder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class EncoderImpl implements Encoder {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public EncoderImpl(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public String encode(String toHash) {
        return bCryptPasswordEncoder.encode(toHash);
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }
}

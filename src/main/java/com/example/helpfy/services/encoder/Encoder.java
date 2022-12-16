package com.example.helpfy.services.encoder;

public interface Encoder {
    String encode(String toHash);

    boolean matches(String rawPassword, String encodedPassword);
}

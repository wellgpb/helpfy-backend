package com.example.helpfy.services.encoder;

import org.springframework.stereotype.Component;

public interface Encoder {
    String encode(String toHash);
}

package com.example.helpfy.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExceptionResponse {
    private int status;
    private List<String> message;
}

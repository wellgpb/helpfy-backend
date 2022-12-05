package com.example.helpfy.dtos.question;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class QuestionRequestPUT {
    private String body;
    private String title;
    private Set<String> tags;
}

package com.example.helpfy.dtos.question;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Builder
@Getter
public class QuestionRequest {
    @NotEmpty(message = "O corpo da questão não pode ser vazio.")
    private String body;
    @NotEmpty(message = "O título da questão não pode ser vazio.")
    private String title;
    private Set<String> tags;
    private boolean answered;

}

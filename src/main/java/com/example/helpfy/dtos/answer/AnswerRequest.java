package com.example.helpfy.dtos.answer;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AnswerRequest {
    @NotEmpty(message = "A resposta não pode ser vazia.")
    private String body;

}
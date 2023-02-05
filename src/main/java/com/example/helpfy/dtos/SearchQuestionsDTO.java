package com.example.helpfy.dtos;

import com.example.helpfy.models.Question;
import lombok.Getter;

import java.util.List;

@Getter
public class SearchQuestionsDTO {
    private final List<Question> questions;
    private final long totalElements;

    public SearchQuestionsDTO(List<Question> questions, long totalElements) {
        this.questions = questions;
        this.totalElements = totalElements;
    }
}

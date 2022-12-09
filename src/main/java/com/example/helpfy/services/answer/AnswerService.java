package com.example.helpfy.services.answer;

import com.example.helpfy.models.Answer;
import com.example.helpfy.models.User;

import java.util.List;

public interface AnswerService {
    List<Answer> getUserAnswers(User user);

    Answer getAnswerById(Long answerId);

    void deleteAnswerById(Long answerId);

    Answer saveAnswer(Answer answer, User user, Long questionId);
}

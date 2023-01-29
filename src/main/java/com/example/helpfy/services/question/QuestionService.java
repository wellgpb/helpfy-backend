package com.example.helpfy.services.question;

import com.example.helpfy.models.Question;
import com.example.helpfy.models.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuestionService {
    Question getQuestionById(Long id);
    Question saveQuestion(Question question, User user);
    Question updateQuestion(Long id, Question newQuestion);
    void deleteQuestion(Long id);
    Question likeQuestion(Long questionId, Long userId);
    Question dislikeQuestion(Long questionId, Long userId);
    List<Question> getQuestionsByAuthor(User user, Pageable pageable);
    Question toggleAnsweredStatus(Long questionId);
}

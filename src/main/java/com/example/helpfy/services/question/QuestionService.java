package com.example.helpfy.services.question;

import com.example.helpfy.models.Question;
import com.example.helpfy.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionService {
    Question getQuestionById(Long id);
    Question saveQuestion(Question question, User user);
    Question updateQuestion(Long id, Question newQuestion);
    void deleteQuestion(Long id);
    Question likeQuestion(Long questionId, Long userId);
    Question dislikeQuestion(Long questionId, Long userId);
    Page<Question> getQuestionsByAuthor(User user, Pageable pageable);
    Question toggleAnsweredStatus(Long questionId);
}

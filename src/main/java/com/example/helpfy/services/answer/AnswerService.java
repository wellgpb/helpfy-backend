package com.example.helpfy.services.answer;

import com.example.helpfy.models.Answer;
import com.example.helpfy.models.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AnswerService {
    List<Answer> getUserAnswers(User user, Pageable pageable);

    Answer getAnswerById(Long answerId);

    void deleteAnswerById(Long answerId);

    Answer saveAnswer(Answer answer, User user, Long questionId);

    Answer updateAnswer(Answer answer, Long answerId);

    Answer likeAnswer(Long answerId, Long userId);

    Answer dislikeAnswer(Long answerId, Long userId);

    Answer toggleSolutionStatus(Long id);
}

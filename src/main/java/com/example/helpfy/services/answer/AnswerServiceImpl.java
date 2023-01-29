package com.example.helpfy.services.answer;

import com.example.helpfy.exceptions.Constants;
import com.example.helpfy.exceptions.NotFoundException;
import com.example.helpfy.models.Answer;
import com.example.helpfy.models.User;
import com.example.helpfy.repositories.AnswerRepository;
import com.example.helpfy.repositories.QuestionRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public AnswerServiceImpl(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public List<Answer> getUserAnswers(User user, Pageable pageable) {
        return answerRepository.findAnswersByAuthor(user, pageable);
    }

    @Override
    public Answer getAnswerById(Long answerId) {
        return answerRepository.findById(answerId).orElseThrow(() -> {
            throw new NotFoundException(Constants.ANSWER_NOT_FOUND);
        });
    }

    @Override
    public void deleteAnswerById(Long answerId) {
        answerRepository.deleteById(answerId);
    }

    @Transactional
    @Override
    public Answer saveAnswer(Answer answer, User user, Long questionId) {
        var question = questionRepository.findById(questionId).orElseThrow(() -> {
            throw new NotFoundException(Constants.QUESTION_NOT_FOUND);
        });

        answer.setAuthor(user);
        answerRepository.save(answer);

        question.getAnswers().add(answer);
        questionRepository.save(question);

        return answer;
    }

    @Override
    public Answer updateAnswer(Answer answerTarget, Long answerId) {
        var answer = getAnswerById(answerId);
        answer.setBody(answerTarget.getBody());

        return answerRepository.save(answer);
    }

    @Override
    public Answer likeAnswer(Long answerId, Long userId) {
        var answer = getAnswerById(answerId);
        updateAnswerLikesAndDislikes(userId, answer.getIdsFromUsersDislikes(), answer.getIdsFromUsersLikes());

        return answerRepository.save(answer);
    }

    @Override
    public Answer dislikeAnswer(Long answerId, Long userId) {
        var answer = getAnswerById(answerId);
        updateAnswerLikesAndDislikes(userId, answer.getIdsFromUsersLikes(), answer.getIdsFromUsersDislikes());

        return answerRepository.save(answer);
    }

    @Override
    public Answer toggleSolutionStatus(Long id) {
        var answer = getAnswerById(id);
        answer.setSolution(!answer.isSolution());
        return answerRepository.save(answer);
    }

    private void updateAnswerLikesAndDislikes(Long userId, Set<Long> oldState, Set<Long> newState) {
        oldState.remove(userId);
        if (newState.contains(userId)) {
            newState.remove(userId);
        } else {
            newState.add(userId);
        }
    }
}

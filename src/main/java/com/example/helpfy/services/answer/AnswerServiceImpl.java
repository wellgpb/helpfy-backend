package com.example.helpfy.services.answer;

import com.example.helpfy.exceptions.Constants;
import com.example.helpfy.exceptions.NotFoundException;
import com.example.helpfy.models.Answer;
import com.example.helpfy.models.Question;
import com.example.helpfy.models.User;
import com.example.helpfy.repositories.AnswerRepository;
import com.example.helpfy.repositories.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public AnswerServiceImpl(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public List<Answer> getUserAnswers(User user) {
        return answerRepository.findAnswersByAuthor(user);
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
        Question question = questionRepository.findById(questionId).orElseThrow(() -> {
            throw new NotFoundException(Constants.QUESTION_NOT_FOUND);
        });

        answer.setAuthor(user);
        answerRepository.save(answer);
        
        question.getAnswers().add(answer);
        questionRepository.save(question);

        return answer;
    }
}

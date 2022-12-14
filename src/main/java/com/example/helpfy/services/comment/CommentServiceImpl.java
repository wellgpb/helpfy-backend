package com.example.helpfy.services.comment;

import com.example.helpfy.models.Answer;
import com.example.helpfy.models.Comment;
import com.example.helpfy.models.User;
import com.example.helpfy.repositories.AnswerRepository;
import com.example.helpfy.repositories.CommentRepository;
import com.example.helpfy.repositories.QuestionRepository;
import com.example.helpfy.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository, QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public Comment addCommentAnswer(Comment comment, Long userId, Long answerId) {
        Optional<User> userOptional = userRepository.findById(userId);

        Optional<Answer> answerOptional = answerRepository.findById(answerId);

        User user = userOptional.get();

        Answer answer = answerOptional.get();
        comment.setAuthor(user);
        commentRepository.save(comment);
        answer.getComments().add(comment);
        answerRepository.save(answer);

        return comment;
    }
}

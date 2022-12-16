package com.example.helpfy.services.comment;

import com.example.helpfy.exceptions.Constants;
import com.example.helpfy.exceptions.NotFoundException;
import com.example.helpfy.models.Answer;
import com.example.helpfy.models.Comment;
import com.example.helpfy.models.Question;
import com.example.helpfy.models.User;
import com.example.helpfy.repositories.AnswerRepository;
import com.example.helpfy.repositories.CommentRepository;
import com.example.helpfy.repositories.QuestionRepository;
import com.example.helpfy.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

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
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new NotFoundException(Constants.USER_NOT_FOUND);
        });

        Answer answer = answerRepository.findById(answerId).orElseThrow(() -> {
            throw new NotFoundException(Constants.ANSWER_NOT_FOUND);
        });

        comment.setAuthor(user);
        commentRepository.save(comment);
        answer.getComments().add(comment);
        answerRepository.save(answer);
        return comment;
    }

    @Override
    public Comment getCommentAnswer(Long commentId, Long answerId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);

        this.validateCommentAndAnswer(answerId, commentId);

        Comment comment = optionalComment.get();

        return comment;
    }

    @Override
    public List<Comment> getAllCommentsAnswer(Long answerId) {
        Answer answer = answerRepository.findById(answerId).orElseThrow(() -> {
            throw new NotFoundException(Constants.ANSWER_NOT_FOUND);
        });

        return answer.getComments();
    }

    @Override
    public Comment updateCommentAnswer(Comment comment, Long commentId, Long answerId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);

        Optional<Answer> optionalAnswer = answerRepository.findById(answerId);

        this.validateCommentAndAnswer(answerId, commentId);

        Comment originalComment = optionalComment.get();
        Answer answer = optionalAnswer.get();

        originalComment.setBody(comment.getBody());
        commentRepository.save(originalComment);
        answerRepository.save(answer);
        return originalComment;
    }

    @Override
    public Comment deleteCommentAnswer(Long commentId, Long answerId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);

        Optional<Answer> optionalAnswer = answerRepository.findById(answerId);

        this.validateCommentAndAnswer(answerId, commentId);

        Comment comment = optionalComment.get();
        Answer answer = optionalAnswer.get();

        answer.getComments().remove(comment);
        answerRepository.save(answer);
        commentRepository.delete(comment);
        return comment;
    }

    public Comment addCommentQuestion(Comment comment, Long userId, Long questionId) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new NotFoundException(Constants.USER_NOT_FOUND);
        });
        Question question = questionRepository.findById(questionId).orElseThrow(() -> {
            throw new NotFoundException(Constants.QUESTION_NOT_FOUND);
        });

        comment.setAuthor(user);
        commentRepository.save(comment);
        question.getComments().add(comment);
        questionRepository.save(question);

        return comment;
    }

    @Override
    public Comment getCommentQuestion(Long commentId, Long questionId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);

        this.validateCommentAndQuestion(questionId, commentId);

        Comment comment = optionalComment.get();

        return comment;
    }

    @Override
    public List<Comment> getAllCommentsQuestion(Long questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> {
            throw new NotFoundException(Constants.QUESTION_NOT_FOUND);
        });

        return question.getComments();
    }

    @Override
    public Comment updateCommentQuestion(Comment comment, Long commentId, Long questionId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);

        Optional<Question> optionalQuestion = questionRepository.findById(questionId);

        this.validateCommentAndQuestion(questionId, commentId);

        Comment originalComment = optionalComment.get();
        Question question = optionalQuestion.get();

        originalComment.setBody(comment.getBody());
        commentRepository.save(originalComment);
        questionRepository.save(question);
        return originalComment;
    }

    @Override
    public Comment deleteCommentQuestion(Long commentId, Long questionId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);

        Optional<Question> optionalQuestion = questionRepository.findById(questionId);

        this.validateCommentAndQuestion(questionId, commentId);

        Comment comment = optionalComment.get();
        Question question = optionalQuestion.get();

        question.getComments().remove(comment);
        questionRepository.save(question);
        commentRepository.delete(comment);
        return comment;
    }

    private void validateCommentAndQuestion(Long questionId, Long commentId) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> {
            throw new NotFoundException(Constants.QUESTION_NOT_FOUND);
        });
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> {
            throw new NotFoundException(Constants.COMMENT_NOT_FOUND);
        });
        if (!question.getComments().contains(comment)) {
            throw new NotFoundException(Constants.COMMENT_NOT_FOUND_IN_QUESTION);
        }
    }

    private void validateCommentAndAnswer(Long answerId, Long commentId) {
        Answer answer = answerRepository.findById(answerId).orElseThrow(() -> {
            throw new NotFoundException(Constants.QUESTION_NOT_FOUND);
        });
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> {
            throw new NotFoundException(Constants.COMMENT_NOT_FOUND);
        });
        if (!answer.getComments().contains(comment)) {
            throw new NotFoundException(Constants.COMMENT_NOT_FOUND_IN_ANSWER);
        }
    }
}

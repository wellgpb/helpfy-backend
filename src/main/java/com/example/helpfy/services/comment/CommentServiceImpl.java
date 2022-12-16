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
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.helpfy.utils.EntitiesUtil.findById;

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

    public Comment addCommentAnswer(Comment comment, User user, Answer answer) {
        comment.setAuthor(user);
        commentRepository.save(comment);
        answer.getComments().add(comment);
        answerRepository.save(answer);
        return comment;
    }

    @Override
    public Comment getCommentAnswer(Long commentId, Answer answer) {
        Comment comment = findById(commentId, commentRepository, Constants.COMMENT_NOT_FOUND);
        checkIfEntityHasComment(answer.getComments(), comment);
        return comment;
    }

    @Override
    public List<Comment> getAllCommentsAnswer(Answer answer) {
        return answer.getComments();
    }

    @Override
    public Comment updateCommentAnswer(Comment comment, Long commentId, Answer answer) {
        Comment originalComment = findById(commentId, commentRepository, Constants.COMMENT_NOT_FOUND);
        originalComment.setBody(comment.getBody());
        commentRepository.save(originalComment);
        answerRepository.save(answer);
        return originalComment;
    }

    @Override
    public Comment deleteCommentAnswer(Long commentId, Answer answer) {
        Comment comment = findById(commentId, commentRepository, Constants.COMMENT_NOT_FOUND);
        answer.getComments().remove(comment);
        answerRepository.save(answer);
        commentRepository.delete(comment);
        return comment;
    }

    public Comment addCommentQuestion(Comment comment, User user, Question question) {
        comment.setAuthor(user);
        commentRepository.save(comment);
        question.getComments().add(comment);
        questionRepository.save(question);

        return comment;
    }

    @Override
    public Comment getCommentQuestion(Long commentId, Question question) {
        Comment comment = findById(commentId, commentRepository, Constants.COMMENT_NOT_FOUND);
        this.checkIfEntityHasComment(question.getComments(), comment);
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
    public Comment updateCommentQuestion(Comment comment, Long commentId, Question question) {
        Comment originalComment = findById(commentId, commentRepository, Constants.COMMENT_NOT_FOUND);
        originalComment.setBody(comment.getBody());
        commentRepository.save(originalComment);
        questionRepository.save(question);
        return originalComment;
    }

    @Override
    public Comment deleteCommentQuestion(Long commentId, Question question) {
        Comment comment = findById(commentId, commentRepository, Constants.COMMENT_NOT_FOUND);
        question.getComments().remove(comment);
        questionRepository.save(question);
        commentRepository.delete(comment);
        return comment;
    }

    private void checkIfEntityHasComment(List<Comment> comments, Comment comment) {
        if (!comments.contains(comment)) {
            throw new NotFoundException(Constants.COMMENT_NOT_FOUND);
        }
    }
}

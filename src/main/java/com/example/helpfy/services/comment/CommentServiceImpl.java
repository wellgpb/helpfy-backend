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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static com.example.helpfy.utils.EntitiesUtil.findById;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public CommentServiceImpl(CommentRepository commentRepository, QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.commentRepository = commentRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    @Transactional
    @Override
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
        this.checkIfEntityHasComment(answer.getComments(), comment);
        return comment;
    }

    @Override
    public List<Comment> getAllCommentsAnswer(Answer answer) {
        return answer.getComments();
    }

    @Transactional
    @Override
    public Comment updateCommentAnswer(Comment comment, Long commentId, Answer answer) {
        Comment originalComment = findById(commentId, commentRepository, Constants.COMMENT_NOT_FOUND);
        this.checkIfEntityHasComment(answer.getComments(), originalComment);
        originalComment.setBody(comment.getBody());
        commentRepository.save(originalComment);
        return originalComment;
    }

    @Transactional
    @Override
    public void deleteCommentAnswer(Long commentId, Answer answer) {
        Comment comment = findById(commentId, commentRepository, Constants.COMMENT_NOT_FOUND);
        answer.getComments().remove(comment);
        answerRepository.save(answer);
        commentRepository.delete(comment);
    }

    @Override
    @Transactional
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
    public List<Comment> getAllCommentsQuestion(Question question) {
        return question.getComments();
    }

    @Transactional
    @Override
    public Comment updateCommentQuestion(Comment comment, Long commentId, Question question) {
        Comment originalComment = findById(commentId, commentRepository, Constants.COMMENT_NOT_FOUND);
        this.checkIfEntityHasComment(question.getComments(), originalComment);
        originalComment.setBody(comment.getBody());
        commentRepository.save(originalComment);
        return originalComment;
    }

    @Override
    @Transactional
    public void deleteCommentQuestion(Long commentId, Question question) {
        Comment comment = findById(commentId, commentRepository, Constants.COMMENT_NOT_FOUND);
        question.getComments().remove(comment);
        questionRepository.save(question);
        commentRepository.delete(comment);
    }

    private void checkIfEntityHasComment(List<Comment> comments, Comment comment) {
        if (!comments.contains(comment)) {
            throw new NotFoundException(Constants.COMMENT_NOT_FOUND);
        }
    }
}

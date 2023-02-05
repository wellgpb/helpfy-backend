package com.example.helpfy.services.comment;

import com.example.helpfy.models.Answer;
import com.example.helpfy.models.Comment;
import com.example.helpfy.models.Question;
import com.example.helpfy.models.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {
    Comment addCommentAnswer(Comment comment, User user, Answer answer);
    Comment getCommentAnswer(Long commentId, Answer answer);
    List<Comment> getAllCommentsAnswer(Answer answer);
    Comment updateCommentAnswer(Comment comment, Long commentId, Answer answer);
    void deleteCommentAnswer(Long commentId, Answer answer);
    Comment addCommentQuestion(Comment comment, User user, Question question);
    Comment getCommentQuestion(Long commentId, Question question);
    List<Comment> getAllCommentsQuestion(Question question);
    Comment updateCommentQuestion(Comment comment, Long commentId, Question question);
    void deleteCommentQuestion(Long commentId, Question question);
}

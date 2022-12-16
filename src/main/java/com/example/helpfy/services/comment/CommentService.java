package com.example.helpfy.services.comment;

import com.example.helpfy.models.Answer;
import com.example.helpfy.models.Comment;
import com.example.helpfy.models.Question;
import com.example.helpfy.models.User;

import java.util.List;

public interface CommentService {
    Comment addCommentAnswer(Comment comment, User user, Answer answer);
    Comment getCommentAnswer(Long commentId, Answer answer);
    List<Comment> getAllCommentsAnswer(Answer answer);
    Comment updateCommentAnswer(Comment comment, Long commentId, Answer answerId);

    Comment deleteCommentAnswer(Long commentId, Answer answer);
    Comment addCommentQuestion(Comment comment, User user, Question question);
    Comment getCommentQuestion(Long commentId, Question question);
    List<Comment> getAllCommentsQuestion(Long questionId);
    Comment updateCommentQuestion(Comment comment, Long commentId,Question question);

    Comment deleteCommentQuestion(Long commentId, Question question);
}

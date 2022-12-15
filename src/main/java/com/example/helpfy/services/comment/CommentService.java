package com.example.helpfy.services.comment;

import com.example.helpfy.models.Comment;

import java.util.List;

public interface CommentService {
    Comment addCommentAnswer(Comment comment, Long userId, Long answerId);
    Comment getCommentAnswer(Long commentId, Long answerid);

    Comment addCommentQuestion(Comment comment, Long userId, Long questionId);
    Comment getCommentQuestion(Long commentId, Long questionId);

}

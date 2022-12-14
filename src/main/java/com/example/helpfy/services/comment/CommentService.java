package com.example.helpfy.services.comment;

import com.example.helpfy.models.Comment;

import java.util.List;

public interface CommentService {
    Comment addCommentAnswer(Comment comment, Long userId, Long answerId);

}

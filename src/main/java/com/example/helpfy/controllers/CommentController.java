package com.example.helpfy.controllers;

import com.example.helpfy.dtos.comment.CommentMapper;
import com.example.helpfy.dtos.comment.CommentRequest;
import com.example.helpfy.dtos.comment.CommentResponse;
import com.example.helpfy.models.Comment;
import com.example.helpfy.services.comment.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@CrossOrigin
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    public CommentController(CommentService commentService, CommentMapper commentMapper) {
        this.commentService = commentService;
        this.commentMapper = commentMapper;
    }

    @PostMapping("/answers/{answerId}/users/{userId}")
    public ResponseEntity<CommentResponse> addCommentAnswer(@RequestBody CommentRequest commentRequest, @PathVariable Long userId, @PathVariable Long answerId){
        Comment comment = commentMapper.toCommentPOST(commentRequest);
        Comment commentResult = this.commentService.addCommentAnswer(comment, userId, answerId);
        CommentResponse commentResponse = commentMapper.fromComment(commentResult);
        return new ResponseEntity<>(commentResponse, HttpStatus.CREATED);
    }
}

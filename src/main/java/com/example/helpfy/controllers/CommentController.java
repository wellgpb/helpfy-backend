package com.example.helpfy.controllers;

import com.example.helpfy.dtos.comment.CommentMapper;
import com.example.helpfy.dtos.comment.CommentRequest;
import com.example.helpfy.dtos.comment.CommentResponse;
import com.example.helpfy.models.Comment;
import com.example.helpfy.services.comment.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("{commentId}/answers/{answerId}")
    public ResponseEntity<CommentResponse> getCommentAnswer(@PathVariable Long commentId, @PathVariable Long answerId){
        Comment commentResult = this.commentService.getCommentAnswer(commentId, answerId);
        CommentResponse commentResponse = commentMapper.fromComment(commentResult);
        return new ResponseEntity<>(commentResponse, HttpStatus.OK);
    }

    @GetMapping("/answers/{answerId}")
    public ResponseEntity<List<CommentResponse>> getAllCommentsAnswer(@PathVariable Long answerId){
        List<Comment> comments = this.commentService.getAllCommentsAnswer(answerId);
        List<CommentResponse> commentsResponse = comments.stream()
                .map(commentMapper::fromComment)
                .collect(Collectors.toList());
        return new ResponseEntity<>(commentsResponse,HttpStatus.OK);

    }

    @PostMapping("/questions/{questionId}/users/{userId}")
    public  ResponseEntity<CommentResponse> addCommentQuestion(@RequestBody CommentRequest commentRequest, @PathVariable Long userId, @PathVariable     Long questionId){
        Comment comment = commentMapper.toCommentPOST(commentRequest);
        Comment commentResult = this.commentService.addCommentQuestion(comment, userId, questionId);
        CommentResponse commentResponse = commentMapper.fromComment(commentResult);
        return new ResponseEntity<>(commentResponse, HttpStatus.CREATED);
    }

    @GetMapping("{commentId}/questions/{questionId}")
    public ResponseEntity<CommentResponse> getCommentQuestion(@PathVariable Long commentId, @PathVariable   Long questionId){
        Comment commentResult = this.commentService.getCommentQuestion(commentId, questionId);
        CommentResponse commentResponse = commentMapper.fromComment(commentResult);
        return new ResponseEntity<>(commentResponse, HttpStatus.OK);
    }


    @GetMapping("/questions/{questionId}")
    public ResponseEntity<List<CommentResponse>> getAllCommentsQuestion(@PathVariable    Long questionId){
        List<Comment> comments = this.commentService.getAllCommentsQuestion(questionId);
        List<CommentResponse> commentsResponse = comments.stream()
                .map(commentMapper::fromComment)
                .collect(Collectors.toList());
        return new ResponseEntity<>(commentsResponse,HttpStatus.OK);
    }
}

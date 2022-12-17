package com.example.helpfy.controllers;

import com.example.helpfy.dtos.comment.CommentMapper;
import com.example.helpfy.dtos.comment.CommentRequest;
import com.example.helpfy.dtos.comment.CommentResponse;
import com.example.helpfy.models.Answer;
import com.example.helpfy.models.Comment;
import com.example.helpfy.models.Question;
import com.example.helpfy.services.answer.AnswerService;
import com.example.helpfy.services.comment.CommentService;
import com.example.helpfy.services.question.QuestionService;
import com.example.helpfy.services.user.UserService;
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
    private final UserService userService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final CommentMapper commentMapper;

    public CommentController(CommentService commentService, UserService userService, QuestionService questionService, AnswerService answerService, CommentMapper commentMapper) {
        this.commentService = commentService;
        this.userService = userService;
        this.questionService = questionService;
        this.answerService = answerService;
        this.commentMapper = commentMapper;
    }

    @PostMapping("/answers/{answerId}/users/{userId}")
    public ResponseEntity<CommentResponse> addCommentAnswer(@RequestBody CommentRequest commentRequest, @PathVariable Long userId, @PathVariable Long answerId){
        var user = userService.getUserById(userId);
        var answer = answerService.getAnswerById(answerId);
        var comment = commentMapper.toCommentPOST(commentRequest);
        var commentResult = this.commentService.addCommentAnswer(comment, user, answer);
        var commentResponse = commentMapper.fromComment(commentResult);
        return new ResponseEntity<>(commentResponse, HttpStatus.CREATED);
    }

    @GetMapping("{commentId}/answers/{answerId}")
    public ResponseEntity<CommentResponse> getCommentAnswer(@PathVariable Long commentId, @PathVariable Long answerId){
        var answer = answerService.getAnswerById(answerId);
        var commentResult = this.commentService.getCommentAnswer(commentId, answer);
        var commentResponse = commentMapper.fromComment(commentResult);
        return new ResponseEntity<>(commentResponse, HttpStatus.OK);
    }

    @GetMapping("/answers/{answerId}")
    public ResponseEntity<List<CommentResponse>> getAllCommentsAnswer(@PathVariable Long answerId){
        var answer = answerService.getAnswerById(answerId);
        var comments = this.commentService.getAllCommentsAnswer(answer);
        var commentsResponse = comments.stream()
                .map(commentMapper::fromComment)
                .collect(Collectors.toList());
        return new ResponseEntity<>(commentsResponse,HttpStatus.OK);

    }

    @DeleteMapping("{commentId}/answers/{answerId}")
    public ResponseEntity<Comment> deleteCommentAnswer(@PathVariable Long commentId, @PathVariable Long answerId){
        var answer = answerService.getAnswerById(answerId);
        var commentResult = this.commentService.deleteCommentAnswer(commentId, answer);
        return new ResponseEntity<>(commentResult, HttpStatus.OK);
    }

    @PutMapping("{commentId}/answers/{answerId}")
    public ResponseEntity<CommentResponse> updateCommentAnswer(@RequestBody CommentRequest commentRequest, @PathVariable Long commentId, @PathVariable Long answerId){
        var comment = this.commentMapper.toCommentPUT(commentRequest);
        Answer answer = answerService.getAnswerById(answerId);
        var commentResult = this.commentService.updateCommentAnswer(comment, commentId, answer);
        var commentResponse = commentMapper.fromComment(commentResult);
        return new ResponseEntity<>(commentResponse, HttpStatus.OK);
    }

    @PostMapping("/questions/{questionId}/users/{userId}")
    public  ResponseEntity<CommentResponse> addCommentQuestion(@RequestBody CommentRequest commentRequest, @PathVariable Long userId, @PathVariable     Long questionId){
        var comment = commentMapper.toCommentPOST(commentRequest);
        var user = userService.getUserById(userId);
        var question = questionService.getQuestionById(questionId);
        var commentResult = this.commentService.addCommentQuestion(comment, user, question);
        var commentResponse = commentMapper.fromComment(commentResult);
        return new ResponseEntity<>(commentResponse, HttpStatus.CREATED);
    }

    @GetMapping("{commentId}/questions/{questionId}")
    public ResponseEntity<CommentResponse> getCommentQuestion(@PathVariable Long commentId, @PathVariable   Long questionId){
        var question = questionService.getQuestionById(questionId);
        var commentResult = this.commentService.getCommentQuestion(commentId, question);
        var commentResponse = commentMapper.fromComment(commentResult);
        return new ResponseEntity<>(commentResponse, HttpStatus.OK);
    }


    @GetMapping("/questions/{questionId}")
    public ResponseEntity<List<CommentResponse>> getAllCommentsQuestion(@PathVariable    Long questionId){
        var question = questionService.getQuestionById(questionId);
        var comments = this.commentService.getAllCommentsQuestion(question);
        var commentsResponse = comments.stream()
                .map(commentMapper::fromComment)
                .collect(Collectors.toList());
        return new ResponseEntity<>(commentsResponse,HttpStatus.OK);
    }

    @PutMapping("{commentId}/questions/{questionId}")
    public ResponseEntity<CommentResponse> updateCommentQuestion(@RequestBody CommentRequest commentRequest, @PathVariable Long commentId, @PathVariable   Long questionId){
        var comment = this.commentMapper.toCommentPUT(commentRequest);
        Question question = questionService.getQuestionById(questionId);
        var commentResult = this.commentService.updateCommentQuestion(comment, commentId, question);
        var commentResponse = commentMapper.fromComment(commentResult);
        return new ResponseEntity<>(commentResponse, HttpStatus.OK);
    }

    @DeleteMapping("{commentId}/questions/{questionId}")
    public ResponseEntity<Comment> deleteCommentQuestion(@PathVariable Long commentId, @PathVariable    Long questionId){
        var question = questionService.getQuestionById(questionId);
        var commentResult = this.commentService.deleteCommentQuestion(commentId, question);
        return new ResponseEntity<>(commentResult, HttpStatus.OK);
    }
}

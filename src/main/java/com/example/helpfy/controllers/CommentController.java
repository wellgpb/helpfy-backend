package com.example.helpfy.controllers;

import com.example.helpfy.dtos.comment.CommentMapper;
import com.example.helpfy.dtos.comment.CommentRequest;
import com.example.helpfy.dtos.comment.CommentResponse;
import com.example.helpfy.models.Answer;
import com.example.helpfy.models.Comment;
import com.example.helpfy.models.Question;
import com.example.helpfy.models.User;
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
        User user = userService.getUserById(userId);
        Answer answer = answerService.getAnswerById(answerId);
        Comment comment = commentMapper.toCommentPOST(commentRequest);
        Comment commentResult = this.commentService.addCommentAnswer(comment, user, answer);
        CommentResponse commentResponse = commentMapper.fromComment(commentResult);
        return new ResponseEntity<>(commentResponse, HttpStatus.CREATED);
    }

    @GetMapping("{commentId}/answers/{answerId}")
    public ResponseEntity<CommentResponse> getCommentAnswer(@PathVariable Long commentId, @PathVariable Long answerId){
        Answer answer = answerService.getAnswerById(answerId);
        Comment commentResult = this.commentService.getCommentAnswer(commentId, answer);
        CommentResponse commentResponse = commentMapper.fromComment(commentResult);
        return new ResponseEntity<>(commentResponse, HttpStatus.OK);
    }

    @GetMapping("/answers/{answerId}")
    public ResponseEntity<List<CommentResponse>> getAllCommentsAnswer(@PathVariable Long answerId){
        Answer answer = answerService.getAnswerById(answerId);
        List<Comment> comments = this.commentService.getAllCommentsAnswer(answer);
        List<CommentResponse> commentsResponse = comments.stream()
                .map(commentMapper::fromComment)
                .collect(Collectors.toList());
        return new ResponseEntity<>(commentsResponse,HttpStatus.OK);

    }

    @DeleteMapping("{commentId}/answers/{answerId}")
    public ResponseEntity<Comment> deleteCommentAnswer(@PathVariable Long commentId, @PathVariable Long answerId){
        Answer answer = answerService.getAnswerById(answerId);
        Comment commentResult = this.commentService.deleteCommentAnswer(commentId, answer);
        return new ResponseEntity<>(commentResult, HttpStatus.OK);
    }

    @PutMapping("{commentId}/answers/{answerId}")
    public ResponseEntity<CommentResponse> updateCommentAnswer(@RequestBody CommentRequest commentRequest, @PathVariable Long commentId, @PathVariable Long answerId){
        Comment comment = this.commentMapper.toCommentPUT(commentRequest);
        Answer answer = answerService.getAnswerById(answerId);
        Comment commentResult = this.commentService.updateCommentAnswer(comment, commentId, answer);
        CommentResponse commentResponse = commentMapper.fromComment(commentResult);
        return new ResponseEntity<>(commentResponse, HttpStatus.OK);
    }

    @PostMapping("/questions/{questionId}/users/{userId}")
    public  ResponseEntity<CommentResponse> addCommentQuestion(@RequestBody CommentRequest commentRequest, @PathVariable Long userId, @PathVariable     Long questionId){
        Comment comment = commentMapper.toCommentPOST(commentRequest);
        User user = userService.getUserById(userId);
        Question question = questionService.getQuestionById(questionId);
        Comment commentResult = this.commentService.addCommentQuestion(comment, user, question);
        CommentResponse commentResponse = commentMapper.fromComment(commentResult);
        return new ResponseEntity<>(commentResponse, HttpStatus.CREATED);
    }

    @GetMapping("{commentId}/questions/{questionId}")
    public ResponseEntity<CommentResponse> getCommentQuestion(@PathVariable Long commentId, @PathVariable   Long questionId){
        Question question = questionService.getQuestionById(questionId);
        Comment commentResult = this.commentService.getCommentQuestion(commentId, question);
        CommentResponse commentResponse = commentMapper.fromComment(commentResult);
        return new ResponseEntity<>(commentResponse, HttpStatus.OK);
    }


    @GetMapping("/questions/{questionId}")
    public ResponseEntity<List<CommentResponse>> getAllCommentsQuestion(@PathVariable    Long questionId){
        Question question = questionService.getQuestionById(questionId);
        List<Comment> comments = this.commentService.getAllCommentsQuestion(question);
        List<CommentResponse> commentsResponse = comments.stream()
                .map(commentMapper::fromComment)
                .collect(Collectors.toList());
        return new ResponseEntity<>(commentsResponse,HttpStatus.OK);
    }

    @PutMapping("{commentId}/questions/{questionId}")
    public ResponseEntity<CommentResponse> updateCommentQuestion(@RequestBody CommentRequest commentRequest, @PathVariable Long commentId, @PathVariable   Long questionId){
        Comment comment = this.commentMapper.toCommentPUT(commentRequest);
        Question question = questionService.getQuestionById(questionId);
        Comment commentResult = this.commentService.updateCommentQuestion(comment, commentId, question);
        CommentResponse commentResponse = commentMapper.fromComment(commentResult);
        return new ResponseEntity<>(commentResponse, HttpStatus.OK);
    }

    @DeleteMapping("{commentId}/questions/{questionId}")
    public ResponseEntity<Comment> deleteCommentQuestion(@PathVariable Long commentId, @PathVariable    Long questionId){
        Question question = questionService.getQuestionById(questionId);
        Comment commentResult = this.commentService.deleteCommentQuestion(commentId, question);
        return new ResponseEntity<>(commentResult, HttpStatus.OK);
    }
}

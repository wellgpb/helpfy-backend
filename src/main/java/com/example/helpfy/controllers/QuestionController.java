package com.example.helpfy.controllers;

import com.example.helpfy.dtos.question.QuestionMapper;
import com.example.helpfy.dtos.question.QuestionRequest;
import com.example.helpfy.dtos.question.QuestionRequestPUT;
import com.example.helpfy.dtos.question.QuestionResponse;
import com.example.helpfy.services.question.QuestionService;
import com.example.helpfy.services.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionService questionService;
    private final UserService userService;
    private final QuestionMapper questionMapper;

    public QuestionController(QuestionService questionService, UserService userService, QuestionMapper questionMapper) {
        this.questionService = questionService;
        this.userService = userService;
        this.questionMapper = questionMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponse> getQuestionById(@PathVariable Long id) {
        var question = questionService.getQuestionById(id);
        return new ResponseEntity<>(questionMapper.fromQuestionToResponse(question), HttpStatus.OK);
    }

    @PostMapping("/users/{userId}")
    public ResponseEntity<QuestionResponse> saveQuestion(@RequestBody @Valid QuestionRequest questionRequest, @PathVariable Long userId) {
        var user = userService.getUserById(userId);
        var question = questionMapper.fromRequestToQuestion(questionRequest);
        var questionSaved = questionService.saveQuestion(question, user);
        var questionResponse = questionMapper.fromQuestionToResponse(questionSaved);
        return new ResponseEntity<>(questionResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionResponse> updateQuestion(@PathVariable Long id, @RequestBody @Valid QuestionRequestPUT questionRequest) {
        var question = questionMapper.toQuestionPUT(questionRequest);
        var updatedQuestion = questionService.updateQuestion(id, question);
        var questionResponse = questionMapper.fromQuestionToResponse(updatedQuestion);
        return new ResponseEntity<>(questionResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{questionId}/users/{userId}/like")
    public ResponseEntity<QuestionResponse> likeQuestion(@PathVariable Long questionId, @PathVariable Long userId) {
        userService.getUserById(userId);
        var questionLiked = questionService.likeQuestion(questionId, userId);
        var questionResponse = questionMapper.fromQuestionToResponse(questionLiked);
        return new ResponseEntity<>(questionResponse, HttpStatus.OK);
    }

    @PostMapping("/{questionId}/users/{userId}/dislike")
    public ResponseEntity<QuestionResponse> dislikeQuestion(@PathVariable Long questionId, @PathVariable Long userId) {
        userService.getUserById(userId);
        var questionLiked = questionService.dislikeQuestion(questionId, userId);
        var questionResponse = questionMapper.fromQuestionToResponse(questionLiked);
        return new ResponseEntity<>(questionResponse, HttpStatus.OK);
    }
}

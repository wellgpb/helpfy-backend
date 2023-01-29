package com.example.helpfy.controllers;

import com.example.helpfy.dtos.PageResponse;
import com.example.helpfy.dtos.question.QuestionMapper;
import com.example.helpfy.dtos.question.QuestionRequest;
import com.example.helpfy.dtos.question.QuestionRequestPUT;
import com.example.helpfy.dtos.question.QuestionResponse;
import com.example.helpfy.services.question.QuestionService;
import com.example.helpfy.services.user.UserService;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/questions")
@CrossOrigin
public class QuestionController {
    private final QuestionService questionService;
    private final UserService userService;
    private final QuestionMapper questionMapper;

    public QuestionController(QuestionService questionService, UserService userService, QuestionMapper questionMapper) {
        this.questionService = questionService;
        this.userService = userService;
        this.questionMapper = questionMapper;
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<PageResponse<QuestionResponse>> getQuestionsByAuthor(@PathVariable Long userId,
                                                                               @RequestParam(defaultValue = "0") int page,
                                                                               @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        var user = userService.getUserById(userId);
        var questions = questionService.getQuestionsByAuthor(user, pageable);

        var questionsResponse = questions.stream()
                .map(questionMapper::fromQuestionToResponse)
                .collect(Collectors.toList());

        var pageResponse = new PageResponse<>(new PageImpl<>(questionsResponse, pageable, questionsResponse.size()));

        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
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

    @PatchMapping("/{id}")
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

    @PatchMapping("/{questionId}/users/{userId}/like")
    public ResponseEntity<QuestionResponse> likeQuestion(@PathVariable Long questionId, @PathVariable Long userId) {
        userService.getUserById(userId);
        var questionLiked = questionService.likeQuestion(questionId, userId);
        var questionResponse = questionMapper.fromQuestionToResponse(questionLiked);
        return new ResponseEntity<>(questionResponse, HttpStatus.OK);
    }

    @PatchMapping("/{questionId}/users/{userId}/dislike")
    public ResponseEntity<QuestionResponse> dislikeQuestion(@PathVariable Long questionId, @PathVariable Long userId) {
        userService.getUserById(userId);
        var questionLiked = questionService.dislikeQuestion(questionId, userId);
        var questionResponse = questionMapper.fromQuestionToResponse(questionLiked);
        return new ResponseEntity<>(questionResponse, HttpStatus.OK);
    }

    @PatchMapping("/{id}/answered")
    public ResponseEntity<QuestionResponse> toggleAnsweredStatus(@PathVariable Long id) {
        var questionUpdated = questionService.toggleAnsweredStatus(id);
        var questionResponse = questionMapper.fromQuestionToResponse(questionUpdated);
        return new ResponseEntity<>(questionResponse, HttpStatus.OK);
    }
}

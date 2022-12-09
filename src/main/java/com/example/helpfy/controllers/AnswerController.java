package com.example.helpfy.controllers;

import com.example.helpfy.dtos.answer.AnswerMapper;
import com.example.helpfy.dtos.answer.AnswerRequest;
import com.example.helpfy.dtos.answer.AnswerResponse;
import com.example.helpfy.services.answer.AnswerService;
import com.example.helpfy.services.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/answers")
public class AnswerController {
    private final AnswerService answerService;
    private final UserService userService;
    private final AnswerMapper answerMapper;

    public AnswerController(AnswerService answerService, UserService userService, AnswerMapper answerMapper) {
        this.answerService = answerService;
        this.userService = userService;
        this.answerMapper = answerMapper;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<List<AnswerResponse>> getUserAnswers(@PathVariable Long id) {
        var user = userService.getUserById(id);
        var answers = answerService.getUserAnswers(user);
        var answersResponse = answers.stream()
                .map(a -> answerMapper.fromAnswer(a))
                .collect(Collectors.toList());
        return new ResponseEntity<>(answersResponse, HttpStatus.OK);
    }

    @GetMapping("/{answerId}")
    public ResponseEntity<AnswerResponse> getAnswer(@PathVariable Long answerId) {
        var answer = answerService.getAnswerById(answerId);
        var answerResponse = answerMapper.fromAnswer(answer);
        return new ResponseEntity<>(answerResponse, HttpStatus.OK);
    }

    @PostMapping("/users/{userId}/questions/{questionId}")
    public ResponseEntity<AnswerResponse> saveAnswer(@RequestBody @Valid AnswerRequest answerRequest, @PathVariable Long userId, @PathVariable Long questionId) {
        System.out.println("chegou");
        var user = userService.getUserById(userId);
        var answer = answerMapper.toAnswerPOST(answerRequest);
        var savedAnswer = answerService.saveAnswer(answer, user, questionId);
        var answerResponse = answerMapper.fromAnswer(savedAnswer);
        return new ResponseEntity<>(answerResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{answerId}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable Long answerId) {
        answerService.deleteAnswerById(answerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}


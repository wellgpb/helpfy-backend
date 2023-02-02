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
@CrossOrigin
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
        var answersResponse = answerService.getUserAnswers(user).stream()
                .map(answerMapper::fromAnswer)
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
        var user = userService.getUserById(userId);
        var answer = answerMapper.toAnswerPOST(answerRequest);
        var savedAnswer = answerService.saveAnswer(answer, user, questionId);
        var answerResponse = answerMapper.fromAnswer(savedAnswer);
        return new ResponseEntity<>(answerResponse, HttpStatus.CREATED);
    }

    @PatchMapping("/{answerId}")
    public ResponseEntity<AnswerResponse> updateAnswer(@RequestBody @Valid AnswerRequest answerRequest, @PathVariable Long answerId) {
        var newAnswer = answerMapper.toAnswerPUT(answerRequest);
        var updatedAnswer = answerService.updateAnswer(newAnswer, answerId);
        var answerResponse = answerMapper.fromAnswer(updatedAnswer);
        return new ResponseEntity<>(answerResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{answerId}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable Long answerId) {
        answerService.deleteAnswerById(answerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{answerId}/users/{userId}/like")
    public ResponseEntity<AnswerResponse> likeAnswer(@PathVariable Long answerId, @PathVariable Long userId) {
        userService.getUserById(userId);
        var answerLiked = answerService.likeAnswer(answerId, userId);
        var answerResponse = answerMapper.fromAnswer(answerLiked);
        return new ResponseEntity<>(answerResponse, HttpStatus.OK);
    }

    @PatchMapping("/{answerId}/users/{userId}/dislike")
    public ResponseEntity<AnswerResponse> dislikeAnswer(@PathVariable Long answerId, @PathVariable Long userId) {
        userService.getUserById(userId);
        var answerLiked = answerService.dislikeAnswer(answerId, userId);
        var answerResponse = answerMapper.fromAnswer(answerLiked);
        return new ResponseEntity<>(answerResponse, HttpStatus.OK);
    }

    @PatchMapping("/{id}/solution")
    public ResponseEntity<AnswerResponse> toggleAnsweredStatus(@PathVariable Long id) {
        var answerUpdated = answerService.toggleSolutionStatus(id);
        var answerResponse = answerMapper.fromAnswer(answerUpdated);
        return new ResponseEntity<>(answerResponse, HttpStatus.OK);
    }
}


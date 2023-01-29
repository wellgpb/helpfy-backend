package com.example.helpfy.controllers;

import com.example.helpfy.dtos.PageResponse;
import com.example.helpfy.dtos.question.QuestionMapper;
import com.example.helpfy.dtos.question.QuestionResponse;
import com.example.helpfy.services.search.QuestionSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/search")
@CrossOrigin
public class QuestionSearchController {
    @Autowired
    private QuestionSearchService questionSearchService;

    @Autowired
    private QuestionMapper questionMapper;

    @GetMapping
    public ResponseEntity<PageResponse<QuestionResponse>> getAll(@RequestParam(defaultValue = "") String title,
                                                         @RequestParam(required = false) Set<String> tags,
                                                         @RequestParam(defaultValue = "new") String filter,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        var questions = questionSearchService.search(title, tags, filter.toLowerCase(), pageable).stream()
                .map(question -> questionMapper.fromQuestionToResponse(question))
                .collect(Collectors.toList());

        var pageResponse = new PageResponse<>(new PageImpl<>(questions, pageable, questions.size()));
        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

}

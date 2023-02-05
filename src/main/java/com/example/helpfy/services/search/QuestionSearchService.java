package com.example.helpfy.services.search;

import com.example.helpfy.dtos.SearchQuestionsDTO;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface QuestionSearchService {
    public SearchQuestionsDTO search(String title, Set<String> tags, String filter, Pageable pageable);
}

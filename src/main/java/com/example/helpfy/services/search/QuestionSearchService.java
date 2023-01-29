package com.example.helpfy.services.search;

import com.example.helpfy.models.Question;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface QuestionSearchService {
    public List<Question> search(String title, Set<String> tags, String filter, Pageable pageable);
}

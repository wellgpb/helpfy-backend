package com.example.helpfy.repositories;

import com.example.helpfy.models.Question;
import com.example.helpfy.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findQuestionsByAuthor(User author);
}

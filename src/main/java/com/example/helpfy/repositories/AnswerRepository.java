package com.example.helpfy.repositories;

import com.example.helpfy.models.Answer;
import com.example.helpfy.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findAnswersByAuthor(User author);
}

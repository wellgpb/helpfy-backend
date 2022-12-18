package com.example.helpfy.repositories;

import com.example.helpfy.models.Question;
import com.example.helpfy.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query(value = "select * from tb_questions " +
            "       where similarity(title, :title) > 0.1" +
            "       order by similarity(title, :title) desc;", nativeQuery = true)
    List<Question> findBySimilarity(@Param("title") String title);
    List<Question> findQuestionsByAuthor(User author);
}

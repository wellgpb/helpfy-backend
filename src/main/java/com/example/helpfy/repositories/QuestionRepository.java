package com.example.helpfy.repositories;

import com.example.helpfy.models.Question;
import com.example.helpfy.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query(value = "select * from tb_questions where similarity(title, :title) > 0.1 order by similarity(title, :title) desc",
            countQuery = "select count(*) from tb_questions where similarity(title, :title) > 0.1",
            nativeQuery = true)
    Page<Question> findBySimilarity(@Param("title") String title, Pageable pageable);
    Page<Question> findQuestionsByAuthor(User author, Pageable pageable);
}

package com.example.quizapp.dao;

import com.example.quizapp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface QuestionDao extends JpaRepository<Question, Integer> {
    List<Question> findByCategory(String category);

    // TOP (:numQ) - means limit , NEWID()- random
    @Query(value = "SELECT TOP (:numQ) * FROM question q WHERE q.category = :category ORDER BY NEWID()",
            nativeQuery = true)
    List<Question> findRandomQuestionByCategory(String category, int numQ);
}

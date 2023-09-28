package com.example.quizapp.service;

import com.example.quizapp.dao.QuestionDao;
import com.example.quizapp.model.Question;
import com.example.quizapp.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

@Autowired
    QuestionDao questionDao;
    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        try {
            questionDao.save(question);
            return new ResponseEntity<>("Successfully added the question", HttpStatus.CREATED);}
        catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new String(),HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Question> updateQuestion(Integer id, Question question) {
        try {
            Question updateQuestion= questionDao.findById(id).orElseThrow(()->new
                    ResourceNotFoundException("Questions not found with id : "+id));
            updateQuestion.setCategory(question.getCategory());
            updateQuestion.setDifficultyLevel(question.getDifficultyLevel());
            updateQuestion.setQuestionTitle(question.getQuestionTitle());
            updateQuestion.setOption1(question.getOption1());
            updateQuestion.setOption2(question.getOption2());
            updateQuestion.setOption3(question.getOption3());
            updateQuestion.setOption4(question.getOption4());
            updateQuestion.setCorrectAnswer(question.getCorrectAnswer());
            return new ResponseEntity<>(questionDao.save(updateQuestion), HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new Question(),HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteQuestion(Integer id) {
        try {
            questionDao.deleteById(id);
            return new ResponseEntity<>("Successfully deleted the question", HttpStatus.NO_CONTENT);}
        catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new String(),HttpStatus.BAD_REQUEST);
    }
}

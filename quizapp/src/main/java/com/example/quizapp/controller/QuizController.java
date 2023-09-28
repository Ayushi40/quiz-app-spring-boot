package com.example.quizapp.controller;

import com.example.quizapp.model.QuestionWrapper;
import com.example.quizapp.model.Response;
import com.example.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    // url - http://localhost:8080/quiz/create?category=Geography&numQ=3&title=JQuiz
    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(@RequestParam String category ,
                                             @RequestParam int numQ, @RequestParam String title)
    {
        return quizService.createQuiz(category,numQ,title);
    }

    // url - http://localhost:8080/quiz/getQuizQuestions/1
    @GetMapping("getQuizQuestions/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id)
    {
        return quizService.getQuizQuestions(id);
    }

    /**
     * url - http://localhost:8080/quiz/submitQuizResponse/1
     * body -
     * [
     *     {
     *         "id": 6,
     *         "response":"Lucknow"
     *     },
     *     {
     *         "id": 7,
     *         "response":"Bangalore"
     *     },
     *     {
     *         "id": 8,
     *         "response":"Chandigarh"
     *     },
     *     {
     *         "id": 2,
     *         "response":"Paris"
     *     },
     *     {
     *         "id": 6,
     *         "response":"Delhi"
     *     }
     * ]
     */
    @PostMapping("submitQuizResponse/{id}")
    public ResponseEntity<Integer> submitQuizResponse(@PathVariable Integer id, @RequestBody  List<Response> responses)
    {
        return quizService.calculateResult(id, responses);


    }
}

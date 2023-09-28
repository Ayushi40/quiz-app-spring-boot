package com.example.quizapp.service;

import com.example.quizapp.dao.QuestionDao;
import com.example.quizapp.dao.QuizDao;
import com.example.quizapp.model.Question;
import com.example.quizapp.model.QuestionWrapper;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;
    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        List<Question> questions=questionDao.findRandomQuestionByCategory(category,numQ);
        System.out.println("questions"+questions);
        Quiz quiz=new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);
        return new ResponseEntity<>("Created Quiz questions", HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz= quizDao.findById(id);
        // if we are using the Optional then
        // we first need to get the object(quiz) then we get the method(getQuestions())
        List<Question> questionsFromDB=quiz.get().getQuestions();
        List<QuestionWrapper> questionsForUser= new ArrayList<>();
        for(Question q: questionsFromDB)
        {
            QuestionWrapper qw= new QuestionWrapper
                    (q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());

            questionsForUser.add(qw);
        }
        return new ResponseEntity<>(questionsForUser,HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        //either write optional or get - Optional<Quiz> quiz= quizDao.findById(id);
        Quiz quiz= quizDao.findById(id).get();
        List<Question> questions= quiz.getQuestions();

        int rightAnsCount=0;
        int questionIndex=0;
        for(Response response: responses)
        {
//            if(response.getId()==questions.get(questionIndex).getId())
            if(response.getResponse().equals(questions.get(questionIndex).getCorrectAnswer()))
                rightAnsCount++;
            questionIndex++;

        }
        return new ResponseEntity<>(rightAnsCount,HttpStatus.OK);
    }
}

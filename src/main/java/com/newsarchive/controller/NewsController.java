/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newsarchive.controller;

import com.newsarchive.domain.News;
import com.newsarchive.repository.NewsRepository;
import java.time.LocalDate;
import java.util.List;
import javax.transaction.Transactional;
import org.aspectj.weaver.patterns.TypePatternQuestions.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Burilas
 */
@Controller
public class NewsController {

    @Autowired
    private NewsRepository newsRepository;

    @GetMapping("/news")
    public String listAllNews(Model model) {
        //Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, Sort.Direction.DESC, "examDate");
        model.addAttribute("allNews", this.newsRepository.findAll());
        return "all-news";
    }

    @GetMapping("/news/{id}")
    public String viewNews(Model model, @PathVariable Long id) {
        model.addAttribute("news", this.newsRepository.findOne(id));
        return "news";
    }

    @PostMapping("/news")
    public String addNews(@RequestParam String title, @RequestParam String content) {
        News n = new News();
        n.setTitle(title);
        n.setContent(content);
        newsRepository.save(n);
        return "redirect:/news";
    }

    @GetMapping("/")
    public String main(Model model) {
        //Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, Sort.Direction.DESC, "examDate");
        return "redirect:/news";
    }

//    @PostMapping("/exams/{examId}/questions/{questionId}")
//    @Transactional
//    public String addQuestionToExam(@PathVariable Long examId, @PathVariable Long questionId) {
//        Exam exam = examRepository.findById(examId).get();
//        Question question = questionRepository.findById(questionId).get();
//
//        List<Exam> exams = question.getExams();
//        exams.add(exam);
//        question.setExams(exams);
//
//        List<Question> questions = exam.getQuestions();
//        questions.add(question);
//        exam.setQuestions(questions);
//
//        this.questionRepository.save(question);
//        this.examRepository.save(exam);
//        return "redirect:/exams/" + examId;
//    }
}

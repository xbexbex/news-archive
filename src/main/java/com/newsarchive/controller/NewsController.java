/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newsarchive.controller;

import com.newsarchive.domain.News;
import com.newsarchive.repository.NewsRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @GetMapping("/")
    public String listAllNews(Model model) {
        Pageable pageable = new PageRequest(0, 5, Direction.DESC, "date");
        model.addAttribute("allNews", this.newsRepository.findAll(pageable));
        return "all_news";
    }

    @GetMapping("/news/add")
    public String add(Model model) {
        return "news_add";
    }

    @GetMapping("/news/{id}/edit")
    public String viewEditNews(Model model, @PathVariable Long id) {
        News news = this.newsRepository.findOne(id);
        model.addAttribute("news", news);
        return "news_edit";
    }

    @GetMapping("/news/{id}")
    public String viewNews(Model model, @PathVariable Long id) {
        News news = this.newsRepository.findOne(id);
        model.addAttribute("news", news);
        return "news";
    }
    
    @DeleteMapping("/news/{id}/delete")
    public String deleteNews(Model model, @PathVariable Long id) {
        this.newsRepository.delete(id);
        return "news";
    }

    @PostMapping("/news/{id}/edit")
    String editNews(@RequestParam String headline,
            @RequestParam String subheading,
            @RequestParam String imgUrl,
            @RequestParam String content,
            @PathVariable Long id) {
        News news = newsRepository.findOne(id);
        if (news != null) {
            news.setHeadline(headline);
            news.setSubheading(subheading);
            news.setImgUrl(imgUrl);
            news.setContent(content);
            newsRepository.save(news);
        }
        return "redirect:/news/" + id;
    }

    @DeleteMapping("/news/{id}")
    public String deleteNews(Model model, @PathVariable String id) {
        News news = this.newsRepository.findOne(Long.parseLong(id));
        this.newsRepository.delete(news);
        return "redirect:/";
    }

    @PostMapping("/news/add")
    String addNews(@RequestParam String headline,
            @RequestParam String subheading,
            @RequestParam String imgUrl,
            @RequestParam String content,
            @RequestParam String category) {
        News news = new News();
        if (news != null) {
            news.setHeadline(headline);
            news.setSubheading(subheading);
            news.setImgUrl(imgUrl);
            news.setContent(content);
            news.setCategory(category);
            news.setDate(LocalDateTime.now());
            newsRepository.save(news);
        }
        return "redirect:/news/" + news.getId();
    }

    private static String dateToString(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return date.format(formatter); // "1986-04-08 12:30"
    }
}

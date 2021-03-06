/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newsarchive.repository;
import com.newsarchive.domain.News;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Burilas
 */
public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findByCategory(String category);
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lukuvinkkikirjasto.repository;
import com.lukuvinkkikirjasto.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Burilas
 */
public interface BookRepository extends JpaRepository<Book, Long> {
}

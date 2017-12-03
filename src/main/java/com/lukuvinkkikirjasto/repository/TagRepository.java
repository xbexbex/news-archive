/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lukuvinkkikirjasto.repository;
import com.lukuvinkkikirjasto.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Burilas
 */
public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByName(String name);
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lukuvinkkikirjasto.controller;

import com.lukuvinkkikirjasto.domain.Book;
import com.lukuvinkkikirjasto.domain.Tag;
import com.lukuvinkkikirjasto.repository.BookRepository;
import com.lukuvinkkikirjasto.repository.TagRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author Burilas
 */
@Controller
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TagRepository tagRepository;

    @GetMapping("/books")
    public String listAllBook(Model model) {
        //Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, Sort.Direction.DESC, "examDate");
        model.addAttribute("books", this.bookRepository.findAll());
        return "books";
    }

    @GetMapping("/add")
    public String add(Model model) {
        return "book_add";
    }

    @GetMapping("/book/{id}/edit")
    public String viewEditBook(Model model, @PathVariable Long id) {
        Book book = this.bookRepository.findOne(id);

        String tagsString = "";
        List<Tag> tagsArray = book.getTags();
        for (Tag tag : book.getTags()) {
            tagsString += tag.getName() + " ";
        }

        model.addAttribute("book", book);
        model.addAttribute("tags", tagsString.trim());
        return "book_edit";
    }

    @GetMapping("/book/{id}")
    public String viewBook(Model model, @PathVariable Long id) {
        model.addAttribute("book", this.bookRepository.findOne(id));
        return "book";
    }

    @PostMapping("/book/{id}/edit")
    String editBook(@RequestBody MultiValueMap<String, String> formData, @PathVariable Long id) {
        List<String> bookName = formData.get("bookName");
        List<String> tagsList = formData.get("tags");
        List<String> bookDate = formData.get("bookDate");
        List<String> bookISBN = formData.get("bookISBN");
        List<String> bookAuthor = formData.get("bookAuthor");
        Book book = bookRepository.findOne(id);
        if (book != null) {
            book.setName(bookName.get(0));
            book.setDate(bookDate.get(0));
            book.setAuthor(bookAuthor.get(0));
            book.setISBN(bookISBN.get(0));

            List<Tag> tags = book.getTags();
            for (Tag t : tags) {
                List<Book> b = t.getBooks();
                b.remove(book);
                t.setBooks(b);
                this.tagRepository.save(t);
            }
            List<Tag> bookTags = new ArrayList();
            if (tagsList != null) {
                String[] tagString = tagsList.get(0).split(" ");
                for (int i = 0; i < tagString.length; i++) {
                    Tag tag = tagRepository.findByName(tagString[i]);
                    // check if tag exists, create if not
                    if (tag == null) {
                        tag = new Tag();
                        tag.setName(tagString[i]);
                    }
                    bookTags.add(tag);
                    List<Book> tagBooks = tag.getBooks();
                    if (tagBooks == null) {
                        tagBooks = new ArrayList();
                    }
                    tagBooks.add(book);
                    tag.setBooks(tagBooks);
                    tagRepository.save(tag);
                }
            }
            book.setTags(bookTags);
            bookRepository.save(book);
        }
        // Return to mainpage
        return "redirect:/book/" + id;
    }

    @DeleteMapping("/book/{id}")
    public String deleteBook(Model model, @PathVariable String id) {
        Book book = this.bookRepository.findOne(Long.parseLong(id));
        List<Tag> tags = book.getTags();
        for (Tag t : tags) {
            List<Book> b = t.getBooks();
            b.remove(book);
            t.setBooks(b);
            this.tagRepository.save(t);
        }
        this.bookRepository.delete(book);
        return "redirect:/books";
    }

    @PostMapping("/add")
    String addBook(@RequestBody MultiValueMap<String, String> formData) {
        List<String> bookName = formData.get("bookName");
        List<String> tagsList = formData.get("tags");
        List<String> bookDate = formData.get("bookDate");
        List<String> bookISBN = formData.get("bookISBN");
        List<String> bookAuthor = formData.get("bookAuthor");
        if (bookName != null) {
            // Add new book using name
            Book book = new Book();
            book.setName(bookName.get(0));
            book.setDate(bookDate.get(0));
            book.setAuthor(bookAuthor.get(0));
            book.setISBN(bookISBN.get(0));

            bookRepository.save(book);
            List<Tag> bookTags = new ArrayList();
            // Split the tags string and connect them to book
            if (tagsList != null) {
                String[] tags = tagsList.get(0).split(" ");
                for (int i = 0; i < tags.length; i++) {
                    if (!tags[i].isEmpty()) {
                        Tag tag = tagRepository.findByName(tags[i]);
                        // check if tag exists, create if not
                        if (tag == null) {
                            tag = new Tag();
                            tag.setName(tags[i]);
                        }
                        bookTags.add(tag);
                        List<Book> tagBooks = tag.getBooks();
                        if (tagBooks == null) {
                            tagBooks = new ArrayList();
                        }
                        tagBooks.add(book);
                        tag.setBooks(tagBooks);
                        tagRepository.save(tag);
                    }
                }
            }
            book.setTags(bookTags);
            bookRepository.save(book);
        }
        // Return to mainpage
        return "redirect:/books";
    }

    @GetMapping("/")
    public String main(Model model) {
        //Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, Sort.Direction.DESC, "examDate");
        return "main";
    }
}

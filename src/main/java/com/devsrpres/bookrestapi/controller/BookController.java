package com.devsrpres.bookrestapi.controller;

import com.devsrpres.bookrestapi.exception.BookNotFoundException;
import com.devsrpres.bookrestapi.model.Book;
import com.devsrpres.bookrestapi.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @GetMapping("/book")
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @PostMapping("/book")
    public Book createBook( @RequestBody Book book) {
        return bookRepository.save(book);
    }

    @GetMapping("/book/{id}")
    public Book getBookById(@PathVariable(value = "id") Long bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException("Book", "id", bookId));
    }

    @PutMapping("/book/{id}")
    public Book updateBook(@PathVariable(value = "id") Long bookId,
                            @RequestBody Book bookDetails) {

        bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException("Book", "id", bookId));

        bookDetails.setId(bookId);
        Book updateBook = bookRepository.save(bookDetails);
        return updateBook;
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable(value = "id") Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException("Book", "id", bookId));

        bookRepository.delete(book);
        return ResponseEntity.ok().build();
    }



}
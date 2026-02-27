package io.github.bookmanagement.controller;

import io.github.bookmanagement.dto.BookDto;
import io.github.bookmanagement.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<BookDto> create(@RequestBody BookDto bookDto) {
        BookDto created = bookService.create(bookDto);
        return ResponseEntity.ok(created);
    }
}

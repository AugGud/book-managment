package io.github.bookmanagement.controller;

import io.github.bookmanagement.dto.BookDto;
import io.github.bookmanagement.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // should return 201
    // and the location of the newly created book
    @PostMapping
    public ResponseEntity<BookDto> create(@Valid @RequestBody BookDto bookDto, UriComponentsBuilder ucb) {
        BookDto created = bookService.create(bookDto);
        URI locationOfNewBook = ucb
                .path("/books/{id}")
                .buildAndExpand(created.id())
                .toUri();
        return ResponseEntity.created(locationOfNewBook).build();
    }

    @GetMapping("/{requestedId}")
    public ResponseEntity<BookDto> getById(@PathVariable Long requestedId) {
        return ResponseEntity.ok(bookService.findBookById(requestedId));
    }
}
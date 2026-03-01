package io.github.bookmanagement.controller;

import io.github.bookmanagement.dto.BookDto;
import io.github.bookmanagement.service.BookService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    private BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // should return 201
    // and the location of the newly created book
    @PostMapping
    private ResponseEntity<BookDto> create(@Valid @RequestBody BookDto bookDto, UriComponentsBuilder ucb) {
        BookDto created = bookService.saveBook(bookDto);
        URI locationOfNewBook = ucb
                .path("/books/{id}")
                .buildAndExpand(created.id())
                .toUri();
        return ResponseEntity.created(locationOfNewBook).build();
    }

    @GetMapping("/{requestedId}")
    private ResponseEntity<BookDto> getById(@PathVariable Long requestedId) {
        return ResponseEntity.ok(bookService.findBookById(requestedId));
    }

    @GetMapping
    private ResponseEntity<Page<BookDto>> findAll(
            @PageableDefault(
                    size = 3,
                    sort = "title",
                    direction = Sort.Direction.DESC
            )
            Pageable pageable) {

        return ResponseEntity.ok(bookService.findAllBooks(pageable));
    }
}
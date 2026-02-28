package io.github.bookmanagement.service;

import io.github.bookmanagement.dto.BookDto;
import io.github.bookmanagement.entity.Book;
import io.github.bookmanagement.exception.BookNotFoundException;
import io.github.bookmanagement.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // DTO → Entity
    // repository.save()
    // Entity → DTO
    public BookDto create(BookDto dto) {
        Book book = new Book(dto.title(), dto.author());

        Book saved = bookRepository.save(book);

        return new BookDto(
                saved.getId(),
                saved.getTitle(),
                saved.getAuthor());
    }

    public BookDto findBookById(Long requestedId) {
        Book book = bookRepository.findById(requestedId)
                .orElseThrow(() -> new BookNotFoundException(requestedId));

        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor()
        );
    }
}

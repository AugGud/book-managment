package io.github.bookmanagement.dto;

public record BookDto(
        Long id,
        String title,
        String author
) {}
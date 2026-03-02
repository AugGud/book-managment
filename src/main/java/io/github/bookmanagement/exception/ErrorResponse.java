package io.github.bookmanagement.exception;

public record ErrorResponse(
        String message,
        int status
) {}

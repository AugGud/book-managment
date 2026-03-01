package io.github.bookmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BookDto(
        Long id,

        @NotBlank(message = "title can not be blank")
        @Size(min = 2, max = 200)
        String title,

        @NotBlank(message = "author can not be blank")
        @Size(min = 2, max = 200)
        String author
) {}
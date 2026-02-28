package io.github.bookmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BookDto(
        Long id,

        @NotBlank(message = "Ttile can not be blank")
        @Size(max = 200)
        String title,

        @NotBlank(message = "Author can not be blank")
        @Size(max = 200)
        String author
) {}
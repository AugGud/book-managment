package io.github.bookmanagement;

import io.github.bookmanagement.dto.BookDto;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class BookJsonTests {

    private final String expectedSingle = "io/github/bookmanagement/expectedSingle.json";
    ClassPathResource expectedPathSingle = new ClassPathResource(expectedSingle);

    private final String expectedList = "io/github/bookmanagement/expectedList.json";
    ClassPathResource expectedPathList = new ClassPathResource(expectedList);

    @Autowired
    private JacksonTester<BookDto> json;

    @Autowired
    private JacksonTester<BookDto[]> jsonList;

    private BookDto[] bookDtos;

    @BeforeEach
    void setUp() {
            bookDtos = Arrays.array(
                    new BookDto(2L, "Harry Potter", "J. K. Rowling"),
                    new BookDto(3L, "The Hobbit", "J. R. R. Tolkien"),
                    new BookDto(4L, "Reverend Insanity", "Gu Zhen Ren"));
    }

    @Test
    void shouldSerializeBookDtoCorrectly() throws IOException {
        BookDto dto  = bookDtos[0];

        // Turn our example object into JSON
        var written = json.write(dto);

        // Checks if serialization is working properly
        assertThat(written).isStrictlyEqualToJson(expectedPathSingle);
    }

    @Test
    void shouldDeserializeBookDtoCorrectly() throws IOException {
        String expected = """
                {
                  "id": 2,
                  "title": "Harry Potter",
                  "author": "J. K. Rowling"
                }
                """;

        var parsed = json.parseObject(expected);

        assertThat(parsed.id()).isEqualTo(2L);
        assertThat(parsed.title()).isEqualTo("Harry Potter");
        assertThat(parsed.author()).isEqualTo("J. K. Rowling");
    }

    @Test
    void shouldSerializeListOfBookDtoCorrectly () throws IOException {
        assertThat(jsonList.write(bookDtos)).isStrictlyEqualToJson(expectedPathList);
    }

    @Test
    void shouldDeserializeListOfBookDtoCorrectly () throws IOException {
        String expected = """
                [
                  {"id": 2, "title": "Harry Potter" , "author": "J. K. Rowling"},
                  {"id": 3, "title": "The Hobbit" , "author": "J. R. R. Tolkien"},
                  {"id": 4, "title": "Reverend Insanity", "author": "Gu Zhen Ren" }
                ]
                """;

        assertThat(jsonList.parse(expected)).isEqualTo(bookDtos);
    }
}
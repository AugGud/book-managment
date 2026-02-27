package io.github.bookmanagement;

import io.github.bookmanagement.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class BookJsonTests {

    private final String expected = "io/github/bookmanagement/expected.json";
    ClassPathResource expectedPath = new ClassPathResource(expected);

    @Autowired
    private JacksonTester<Book> json;

    @Test
    void shouldSerializeBookCorrectly() throws IOException {
        Book book  = new Book("Harry Potter", "J. K. Rowling");

        // Turn our example object into JSON
        var written = json.write(book);

        // Checks if serialization is working properly
        assertThat(written).isStrictlyEqualToJson(expectedPath);
    }

    @Test
    void shouldDeserializeBookCorrectly() throws IOException {
        var parsedBook = json.read(expectedPath).getObject();

        assertThat(parsedBook.getTitle()).isEqualTo("Harry Potter");
        assertThat(parsedBook.getAuthor()).isEqualTo("J. K. Rowling");
    }
}

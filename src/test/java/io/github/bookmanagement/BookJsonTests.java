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

    @Autowired
    private JacksonTester<Book> json;

    @Test
    void BookSerializationTest() throws IOException {
        Book book  = new Book("Harry Potter", "J. K. Rowling");

        assertThat(json.write(book)).isStrictlyEqualToJson(new ClassPathResource("io/github/bookmanagement/expected.json"));
    }
}

package io.github.bookmanagement;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.github.bookmanagement.dto.BookDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void shouldReturnABookUsingAnId() {
        ResponseEntity<String> getResponse = restTemplate.getForEntity("/books/2", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        Number id = documentContext.read("$.id");
        assertThat(id).isEqualTo(2);

        String title = documentContext.read("$.title");
        assertThat(title).isEqualTo("Harry Potter");

        String author = documentContext.read("$.author");
        assertThat(author).isEqualTo("J. K. Rowling");
    }

    @Test
    void shouldReturn404ForUnknownBook() {
        ResponseEntity<String> getResponse = restTemplate.getForEntity("/books/999", String.class);

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DirtiesContext
    void shouldCreateANewBook() {
        BookDto newBookDto = new BookDto(null, "Harry Potter", "J. K. Rowling");
        ResponseEntity<Void> createResponse = restTemplate.postForEntity("/books", newBookDto, Void.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI locationOfNewBook = createResponse.getHeaders().getLocation();
        ResponseEntity<String> getResponse = restTemplate.getForEntity(locationOfNewBook, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        Number id = documentContext.read("$.id");
        assertThat(id).isEqualTo(1);

        String title = documentContext.read("$.title");
        assertThat(title).isEqualTo("Harry Potter");

        String author = documentContext.read("$.author");
        assertThat(author).isEqualTo("J. K. Rowling");
    }
}

package io.github.bookmanagement;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.github.bookmanagement.dto.BookDto;
import net.minidev.json.JSONArray;
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

    @Test
    void shouldReturnAListOfBooks() {
        ResponseEntity<String> getAllResponse = restTemplate.getForEntity("/books", String.class);
        assertThat(getAllResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(getAllResponse.getBody());

        int bookCount = documentContext.read("$.content.length()");
        assertThat(bookCount).isEqualTo(3);

        JSONArray ids = documentContext.read("$.content[*].id");
        assertThat(ids).containsExactlyInAnyOrder(2, 3, 4);

        JSONArray titles = documentContext.read("$.content[*].title");
        assertThat(titles).containsExactlyInAnyOrder("Harry Potter", "The Hobbit", "Reverend Insanity");

        JSONArray authors = documentContext.read("$.content[*].author");
        assertThat(authors).containsExactlyInAnyOrder("J. K. Rowling", "J. R. R. Tolkien", "Gu Zhen Ren");
    }

    @Test
    void shouldReturnAPageOfBooks() {
        ResponseEntity<String> getAllResponse = restTemplate.getForEntity("/books?page=0&size=1", String.class);
        assertThat(getAllResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(getAllResponse.getBody());
        int bookCount = documentContext.read("$.content.length()");
        assertThat(bookCount).isEqualTo(1);
    }

    @Test
    void shouldReturnASortedPageOfBooks() {
        ResponseEntity<String> getAllResponse = restTemplate.getForEntity("/books?page=0&size=3&sort=title,asc", String.class);
        assertThat(getAllResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(getAllResponse.getBody());
        int bookCount = documentContext.read("$.content.length()");
        assertThat(bookCount).isEqualTo(3);

        JSONArray titles = documentContext.read("$.content[*].title");
        assertThat(titles).containsExactly("Harry Potter", "Reverend Insanity", "The Hobbit");
    }

    @Test
    void shouldReturnADefaultSortedPageOfBooks() {
        ResponseEntity<String> getAllResponse = restTemplate.getForEntity("/books", String.class);
        assertThat(getAllResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(getAllResponse.getBody());
        int bookCount = documentContext.read("$.content.length()");
        assertThat(bookCount).isEqualTo(3);

        JSONArray titles = documentContext.read("$.content[*].title");
        assertThat(titles).containsExactly("The Hobbit", "Reverend Insanity", "Harry Potter");
    }
}

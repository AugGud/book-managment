package io.github.bookmanagement;

import io.github.bookmanagement.dto.BookDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTests {

    @Autowired
    TestRestTemplate restTemplate;

//    @Test
//    void shouldReturnABookUsingAnId() {
//        ResponseEntity<String> getResponse = restTemplate.getForEntity("/books/99");
//    }

    @Test
    void shouldCreateANewBook() {
        BookDto newBookDto = new BookDto(null, "Harry Potter", "J. K. Rowling");
        ResponseEntity<Void> createResponse = restTemplate.postForEntity("/books", newBookDto, Void.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}

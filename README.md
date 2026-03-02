# Book Management API

This project was built as part of my backend learning journey to practice
Spring Boot fundamentals and REST API design.

The focus was on:
- clean layered architecture
- DTO-based API boundaries
- validation and error handling
- pagination and sorting
- Lots of basic integration testing

## Tech stack

- Java 21
- Spring Boot 3.4.3
- Spring Web
- Spring Data JPA
- H2 in-memory database
- Maven
- JUnit 5 / Spring Boot Test

## Project structure

```text
src/main/java/io/github/bookmanagement
├── controller     # REST endpoints
├── dto            # Request/response DTOs + validation
├── entity         # JPA entity
├── exception      # Custom exception + global handler
├── repository     # Spring Data repository
└── service        # Business logic
```

## Requirements

- JDK 21
- Maven 3.9+ (or use the included Maven wrapper `./mvnw`)

## Run locally

1. Start the app:

```bash
./mvnw spring-boot:run
```

2. API base URL:

```text
http://localhost:8080
```

3. Optional H2 console (enabled):

```text
http://localhost:8080/h2-console
```

Use:
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: *(empty)*

## Database behavior

- Uses in-memory H2, so data resets on restart.
- Schema is created/dropped automatically (`create-drop`).
- Seed data is loaded from `src/main/resources/data.sql`.

Default seeded books:

- `2` - Harry Potter / J. K. Rowling
- `3` - The Hobbit / J. R. R. Tolkien
- `4` - Reverend Insanity / Gu Zhen Ren

## API reference

Base path: `/books`

### Create book

- `POST /books`
- Returns: `201 Created` + `Location` header + created book

### Get book by ID

- `GET /books/{id}`
- Returns: `200 OK`
- Not found: `404 Not Found`

### List books (paginated)

- `GET /books`
- Returns: Spring Data `Page<BookDto>`

Defaults if not provided:
- `size=3`
- `sort=title,desc`

### Update book

- `PUT /books/{id}`
- Returns: `200 OK` with updated book
- Not found: `404 Not Found`

### Delete book

- `DELETE /books/{id}`
- Returns: `204 No Content`
- Not found: `404 Not Found`

## Validation and errors

`title` and `author` rules:

- required (`@NotBlank`)
- length between 2 and 200 (`@Size(min = 2, max = 200)`)

Validation failure returns `400 Bad Request`.

Other common errors:

- `404` -> `{"message":"Book not found with id: 999","status":404}`
- `500` -> `{"message":"Unexpected server error. Please try again later.","status":500}`

## Testing

Run all tests:

```bash
./mvnw test
```

## Sources of learning

This project was built while learning Spring Boot through the official
Spring Academy REST API course:
https://spring.academy/courses/building-a-rest-api-with-spring-boot

AI tools were occasionally used for documentation suggestions
and code-review style feedback.

## Future improvements

- Return field-level validation errors

- Add OpenAPI documentation

- Replace H2 with PostgreSQL

- Add Docker support

- Add Spring Security.
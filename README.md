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

- `404` -> `{"message":"Book not found","status":404}`
- `500` -> `{"message":"Server crashed","status":500}`

## Testing

Run all tests:

```bash
./mvnw test
```

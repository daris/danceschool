# Dance School API

A **Spring Boot 3** REST API for a Dance School application with **JWT authentication**, user registration, and attendance management. Protected endpoints require JWT tokens. Swagger UI is included for easy testing.

---

## Features

- User registration and login
- JWT-based authentication
- Secure password storage with BCrypt
- Attendance and pass management
- Swagger UI for API documentation and testing

---

## Technologies

- Java 24
- Spring Boot 3
- Spring Security 6
- JWT (JJWT)
- Spring Data JPA
- Lombok
- Springdoc OpenAPI (Swagger UI)

---

## Setup

### 1. Clone the repository

```bash
git clone https://github.com/daris/danceschool.git
cd danceschool
```

### 2. Build and Run

```bash
docker compose up -d
```

API will be available at `http://localhost:8080`.

---

### Local development

```bash
docker compose up -d db
./gradlew clean build
./gradlew bootRun
```

---

### Access Protected Endpoints

Include JWT in request header:

```
Authorization: Bearer <accessToken>
```

---

## Swagger UI

- URL: `http://localhost:8080/swagger-ui.html`
- Use the **Authorize** button to enter your JWT for testing protected endpoints.

---

## Security Notes

- Passwords are stored using **BCrypt hashing**.
- JWT tokens expire after 24 hours (configurable in `JwtService`).
- Public endpoints:
    - `/api/auth/register`
    - `/api/auth/login`
    - Swagger endpoints (`/swagger-ui/**`, `/v3/api-docs/**`)
- All other endpoints require JWT authentication.

---

## References

- [Spring Boot 3 Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Security JWT Guide](https://www.baeldung.com/spring-security-oauth-jwt)
- [Springdoc OpenAPI](https://springdoc.org/)
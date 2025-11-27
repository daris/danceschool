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

- Java 21
- Spring Boot 3
- Spring Security 6
- JWT (JJWT)
- Spring Data JPA
- Lombok
- Springdoc OpenAPI (Swagger UI)
- Kafka

---

## Setup

### 1. Clone the repository

```bash
git clone https://github.com/daris/danceschool.git
git clone https://github.com/daris/danceschool-frontend.git
git clone https://github.com/daris/danceschool-notifications.git
cd danceschool
```

### 2. Build and Run

Production version requires danceschool-frontend and danceschool-notifications repository cloned in the same directory as this repository.

```bash
docker compose -f docker-compose.prod.yml up -d
```

Application will be available at `https://localhost/`.

You can log in on admin account:
```
Username: admin
Password: securepass123
```

Credentials are defined in docker-compose.prod.yml: `services.app.environment.ADMIN_USERNAME`

---

### Local development

```bash
docker compose up -d
```

Run backend using gradle or by starting app in IntelliJ Idea

```bash
./gradlew clean build
./gradlew bootRun
```

API will be available at `https://localhost/swagger-ui/index.html`.

You can also run `pnpm dev` from danceschool-frontend directory to start frontend in dev mode. Frontend will be available at `https://localhost`.

---

### Run tests

```bash
./gradlew clean test
```

---
### Access Protected Endpoints

Include JWT in request header:

```
Authorization: Bearer <accessToken>
```

---

## Swagger UI

- URL: `https://localhost/swagger-ui/index.html`
- Use the **Authorize** button to enter your JWT for testing protected endpoints.

---

## Security Notes

- Passwords are stored using **BCrypt hashing**.
- JWT tokens expire after 24 hours (configurable in `JwtService`).
- Public endpoints:
    - `/auth/register`
    - `/auth/login`
    - Swagger endpoints (`/swagger-ui/**`, `/v3/api-docs/**`)
- All other endpoints require JWT authentication.

---

## References

- [Spring Boot 3 Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Security JWT Guide](https://www.baeldung.com/spring-security-oauth-jwt)
- [Springdoc OpenAPI](https://springdoc.org/)
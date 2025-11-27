# ---- Build Stage ----
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

# Copy Gradle wrapper and build scripts first for caching
COPY gradlew settings.gradle build.gradle ./
COPY gradle gradle

# Make sure Gradle wrapper is executable (works cross-platform)
RUN chmod +x gradlew

# Download dependencies (layer caching)
RUN ./gradlew dependencies --no-daemon || return 0

# Copy the rest of the source code
COPY src src

# Build the Spring Boot fat jar
RUN ./gradlew bootJar --no-daemon

# ---- Runtime Stage ----
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy built jar from build stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Create a non-root user for safety
RUN useradd -ms /bin/bash spring
USER spring

# Expose default Spring Boot port
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
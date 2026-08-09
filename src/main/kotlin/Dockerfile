# Build Stage: Uses Gradle with JDK 21 to build the application
FROM gradle:8.5-jdk21 AS build
WORKDIR /app

# Copy dependency files first for better layer caching
COPY build.gradle.kts settings.gradle.kts ./
COPY gradle.properties* ./

# Download dependencies (this layer will be cached if dependencies don't change)
RUN gradle dependencies --no-daemon || true

# Copy source code and build the application
COPY src ./src
RUN gradle buildFatJar --no-daemon

# Runtime Stage: Uses a lightweight JRE image
FROM eclipse-temurin:21-jre
WORKDIR /app

# Create a non-root user for security
RUN addgroup -S ktor && adduser -S ktor -G ktor

# Copy the built JAR from the build stage
COPY --from=build /app/build/libs/*-all.jar app.jar

# Change ownership to the non-root user
RUN chown -R ktor:ktor /app
USER ktor

# Expose the default Ktor port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

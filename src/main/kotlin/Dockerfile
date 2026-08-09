# Build stage - uses Gradle with JDK 17 (multi-arch)
FROM --platform=linux/amd64 gradle:8.5-jdk17 AS build
WORKDIR /app
COPY build.gradle.kts settings.gradle.kts ./
COPY src ./src
RUN gradle build --no-daemon

# Runtime stage - uses Amazon Corretto 17 (multi-arch, no alpine)
FROM --platform=linux/amd64 amazoncorretto:17
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

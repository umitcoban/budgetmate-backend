# 1) Build
FROM gradle:9-jdk-25-and-25-alpine AS builder
WORKDIR /app

# Gradle cache
COPY build.gradle.kts settings.gradle.kts /app/
COPY gradle /app/gradle

# Dummy build
RUN gradle --no-daemon build -x test || return 0

# Copy Resources
COPY . /app

# Skip testing
RUN gradle --no-daemon clean bootJar -x test

# 2) lightweight Java runtime
FROM eclipse-temurin:25-alpine

ENV APP_USER=spring
ENV APP_HOME=/app

RUN addgroup -S $APP_USER && adduser -S $APP_USER -G $APP_USER
WORKDIR $APP_HOME

# Get Jar from builder
COPY --from=builder /app/build/libs/*.jar app.jar

# Basic Health Check
HEALTHCHECK --interval=30s --timeout=5s --start-period=30s \
  CMD wget -qO- http://localhost:8080/actuator/health | grep UP || exit 1

USER $APP_USER

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

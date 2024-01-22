FROM openjdk:8-jdk-alpine

WORKDIR /app
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY app/src src

CMD ["./gradlew", "build"]

FROM openjdk:11-jdk


WORKDIR /app
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY app/src src

CMD ["./gradlew", "build"]

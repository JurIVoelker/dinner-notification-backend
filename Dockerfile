FROM gradle:jdk21-corretto AS build
WORKDIR /app

COPY gradlew .
COPY gradle ./gradle
COPY build.gradle.kts settings.gradle.kts ./
COPY src ./src

ENV BASIC_AUTH_USER=build
ENV BASIC_AUTH_PASSWORD=build

RUN chmod +x gradlew && ./gradlew build -x test --no-daemon

FROM openjdk:21 AS final
WORKDIR /app

COPY --from=build /app/build/libs/dinner-notification-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]
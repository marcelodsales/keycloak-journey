FROM eclipse-temurin:25-jdk-alpine AS build

WORKDIR /app
COPY pom.xml ./
COPY src/ ./src/

RUN apk add --no-cache maven && \
    mvn package -DskipTests -Dquarkus.package.jar.type=uber-jar

FROM eclipse-temurin:25-jre-alpine

WORKDIR /app
COPY --from=build /app/target/*-runner.jar app.jar

EXPOSE 8081

CMD ["java", "-jar", "app.jar"]

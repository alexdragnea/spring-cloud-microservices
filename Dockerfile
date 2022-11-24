FROM maven:3.5.2-jdk-8-alpine AS MAVEN_TOOL_CHAIN
COPY pom.xml /tmp/
COPY rating-service /tmp/rating-service/
COPY book-service /tmp/book-service/
WORKDIR /tmp/
RUN mvn clean install -Pdocker

FROM adoptopenjdk/openjdk11:alpine-jre

COPY --from=MAVEN_TOOL_CHAIN /tmp/api/target/multi-module-spring-docker.jar app.jar

RUN sh -c 'touch /app.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
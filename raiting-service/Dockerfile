FROM maven:3.6.3-jdk-11-slim AS build

COPY src /home/raiting-service/src
COPY pom.xml /home/raiting-service
RUN cd -
COPY pom.xml /home/
WORKDIR /home/
RUN mvn clean install -Pdocker

FROM adoptopenjdk/openjdk11:alpine-jre

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
COPY --from=build /home/app/target/rating-service-0.0.1-SNAPSHOT.jar rating-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/rating-service-0.0.1-SNAPSHOT.jar"]

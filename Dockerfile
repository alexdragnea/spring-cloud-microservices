#FROM maven:3.6.3-jdk-11-slim AS build
#
#COPY src /home/raiting-service/src
#COPY pom.xml /home/raiting-service
#RUN cd -
#COPY pom.xml /home/
#WORKDIR /home/
#RUN mvn clean install -Pdocker
#
#FROM adoptopenjdk/openjdk11:alpine-jre
#
#RUN addgroup -S spring && adduser -S spring -G spring
#USER spring:spring
#COPY --from=build /home/app/target/rating-service-0.0.1-SNAPSHOT.jar rating-service-0.0.1-SNAPSHOT.jar
#ENTRYPOINT ["java","-jar","/rating-service-0.0.1-SNAPSHOT.jar"]

# cache as most as possible in this multistage dockerfile.
FROM maven:3.6-alpine as DEPS

WORKDIR /opt/app
COPY raiting-service/pom.xml raiting-service/pom.xml
COPY book-service/pom.xml book-service/pom.xml


COPY raiting-service/pom.xml .
RUN mvn -B -e -C org.apache.maven.plugins:maven-dependency-plugin:3.1.2:go-offline

# if you have modules that depends each other, you may use -DexcludeArtifactIds as follows
# RUN mvn -B -e -C org.apache.maven.plugins:maven-dependency-plugin:3.1.2:go-offline -DexcludeArtifactIds=module1

# Copy the dependencies from the DEPS stage with the advantage
# of using docker layer caches. If something goes wrong from this
# line on, all dependencies from DEPS were already downloaded and
# stored in docker's layers.
FROM maven:3.6-alpine as BUILDER
WORKDIR /opt/app
COPY --from=deps /root/.m2 /root/.m2
COPY --from=deps /opt/app/ /opt/app
COPY raiting-service/src /opt/app/raiting-service/src
COPY book-service/src /opt/app/book-service/src

# use -o (--offline) if you didn't need to exclude artifacts.
# if you have excluded artifacts, then remove -o flag
RUN mvn -B -e -o clean install -DskipTests=true

# At this point, BUILDER stage should have your .jar or whatever in some path
FROM openjdk:8-alpine
WORKDIR /opt/app
COPY --from=builder /opt/app/<path-to-target>/my-1.0.0.jar .
EXPOSE 8080
CMD [ "java", "-jar", "/opt/app/my-1.0.0.jar" ]
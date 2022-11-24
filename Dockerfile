# cache as most as possible in this multistage dockerfile.
FROM maven:3.6-alpine as DEPS

WORKDIR /opt/app
COPY book-service/pom.xml book-service/pom.xml
COPY raiting-service/pom.xml raiting-service/pom.xml
COPY discovery-service/pom.xml discovery-service/pom.xml
COPY gateway/pom.xml gateway/pom.xml

# you get the idea:
# COPY moduleN/pom.xml moduleN/pom.xml

RUN mvn -B -e -C org.apache.maven.plugins:maven-dependency-plugin:3.1.2:go-offline

# if you have modules that depends each other, you may use -DexcludeArtifactIds as follows
# RUN mvn -B -e -C org.apache.maven.plugins:maven-dependency-plugin:3.1.2:go-offline -DexcludeArtifactIds=gateway -DexcludeArtifactIds=discovery-service

# Copy the dependencies from the DEPS stage with the advantage
# of using docker layer caches. If something goes wrong from this
# line on, all dependencies from DEPS were already downloaded and
# stored in docker's layers.
FROM maven:3.6-alpine as BUILDER
WORKDIR /opt/app
COPY --from=deps /root/.m2 /root/.m2
COPY --from=deps /opt/app/ /opt/app
COPY book-service/src /opt/app/book-service/src
COPY raiting-service/src /opt/app/raiting-service/src
COPY discovery-service/src /opt/app/discovery-service/src
COPY gateway/src /opt/app/gateway/src


# use -o (--offline) if you didn't need to exclude artifacts.
# if you have excluded artifacts, then remove -o flag
RUN mvn -B -e -o clean install -DskipTests=true

# At this point, BUILDER stage should have your .jar or whatever in some path
FROM openjdk:8-alpine
WORKDIR /opt/app
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
COPY --from=builder /opt/app/<path-to-target>/my-1.0.0.jar .
EXPOSE 9560
CMD [ "java", "-jar", "/opt/app/my-1.0.0.jar" ]

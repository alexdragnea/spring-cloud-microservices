FROM adoptopenjdk/openjdk11:alpine-jre

ADD target/discovery-service-*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
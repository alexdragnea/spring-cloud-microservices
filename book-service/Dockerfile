FROM adoptopenjdk/openjdk11:alpine-jre

ADD target/book-service-*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
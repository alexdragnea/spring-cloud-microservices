FROM adoptopenjdk/openjdk11:alpine-jre

ADD target/rating-service-*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
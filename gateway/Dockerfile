FROM adoptopenjdk/openjdk11:alpine-jre

ADD target/gateway-*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
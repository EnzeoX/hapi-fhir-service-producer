FROM eclipse-temurin:17-jdk-alpine

MAINTAINER Nikolay Boiko

COPY ./target/app.war /usr/app/

WORKDIR /usr/app

ENTRYPOINT ["java", "-jar", "app.war"]
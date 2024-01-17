FROM adoptopenjdk/openjdk11:latest

MAINTAINER Nikolay Boiko

COPY ./target/app.war /usr/app/

WORKDIR /usr/app

ENTRYPOINT ["java", "-jar", "app.war"]
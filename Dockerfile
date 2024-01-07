FROM eclipse-temurin:21.0.1_12-jre

MAINTAINER Nikolay Boiko

COPY target/*.war opt/app.war

ENTRYPOINT ["java", "-jar", "opt/app.war"]
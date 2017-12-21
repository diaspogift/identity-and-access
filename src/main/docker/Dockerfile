FROM openjdk:8u131-jdk-alpine

MAINTAINER Felicien Fotio Manfo "contact@diaspogift.com"

EXPOSE 8080

WORKDIR /usr/local/bin/

COPY maven/identity-and-access-0.0.1-SNAPSHOT.jar iam.jar

CMD ["java","-Dspring.profiles.active=docker", "-jar","iam.jar"]
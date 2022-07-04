FROM openjdk:8-jdk-alpine
MAINTAINER Fakher SOUAYEH
COPY build/libs/rest-service-0.0.1-SNAPSHOT.jar demo-backend-0.0.1.jar
ENTRYPOINT ["java","-jar","/demo-backend-0.0.1.jar"]
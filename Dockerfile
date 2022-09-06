#FROM openjdk:8-jdk-alpine
#
#ENV DB_SERVER serene_williamson
#
#MAINTAINER Fakher SOUAYEH
#
#COPY build/libs/rest-service-1.1.0-SNAPSHOT.jar demo-backend-1.1.0.jar
#
#ENTRYPOINT ["java","-jar","/demo-backend-1.1.0.jar"]




# using multistage docker build
# ref: https://docs.docker.com/develop/develop-images/multistage-build/
    
# temp container to build using gradle
FROM gradle:7.5.1-jdk11-alpine AS TEMP_BUILD_IMAGE
ENV APP_HOME /usr/app/
#ENV DB_SERVER="172.17.0.2"
WORKDIR $APP_HOME
COPY build.gradle settings.gradle $APP_HOME
  
COPY gradle $APP_HOME/gradle
COPY --chown=gradle:gradle . /home/gradle/src
USER root
RUN chown -R gradle /home/gradle/src
    
RUN gradle build || return 0
COPY . .
RUN gradle clean build

# create database
FROM library/postgres
COPY init.sql /docker-entrypoint-initdb.d/
    
# actual container
FROM openjdk:8-jdk-alpine
ENV ARTIFACT_NAME rest-service-1.1.0-SNAPSHOT.jar
ENV APP_HOME /usr/app/
#ENV DB_SERVER="172.17.0.2"
    
WORKDIR $APP_HOME
COPY --from=TEMP_BUILD_IMAGE $APP_HOME/build/libs/$ARTIFACT_NAME .

    
EXPOSE 8080
ENTRYPOINT exec java -jar ${ARTIFACT_NAME}
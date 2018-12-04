FROM openjdk:8-jre-alpine
MAINTAINER pwittchen
WORKDIR /app
COPY build/libs/app-1.0-all.jar .
RUN ls -la /app
CMD java -jar app-1.0-all.jar

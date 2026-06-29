FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -q dependency:go-offline
COPY src ./src
RUN mvn -q clean package

FROM tomcat:10.1-jdk17-temurin
ENV DB_URL="jdbc:mysql://db:3306/lesson_reservation?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Tokyo&allowPublicKeyRetrieval=true&useSSL=false"
ENV DB_USER="app_user"
ENV DB_PASSWORD="app_password"
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /app/target/lesson-reservation.war /usr/local/tomcat/webapps/ROOT.war

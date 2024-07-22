FROM openjdk:22-jdk-slim



WORKDIR /stock-app

COPY build/libs/*.jar app.jar
COPY build/resources/main/database/stocks.csv stocks.csv

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar", "-Dspring.profiles.active=postgres"]

FROM openjdk:17-jdk-slim

WORKDIR /app

# Correct the filename in COPY command
COPY target/url-shortener-application-0.0.1-SNAPSHOT.jar url-shortener-application.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "url-shortener-application.jar"]

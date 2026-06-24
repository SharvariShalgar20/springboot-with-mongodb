FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY target/journal-app.jar journal-app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "journal-app.jar"]
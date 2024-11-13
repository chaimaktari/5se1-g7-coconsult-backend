FROM openjdk:17-alpine

WORKDIR /app

COPY target/CoConsult-1.0.0.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]

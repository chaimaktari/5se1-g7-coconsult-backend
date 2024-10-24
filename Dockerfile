FROM openjdk:17-alpine

WORKDIR /app

COPY target/CoConsult.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
FROM openjdk:17-alpine

WORKDIR /app

COPY target/spring-boot-security-jwt-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
FROM openjdk:17-alpine

# Install curl if not already available
RUN apk add --no-cache curl

WORKDIR /app

# Download the .jar file from Nexus
#RUN curl -o app.jar -L "http://142.767.33.21:8081/repository/maven-releases/com/bezkoder/DevDynamos/66/DevDynamos-66.jar"
COPY target/spring-boot-security-jwt-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# Run the jar file
CMD ["java", "-jar", "app.jar"]

FROM openjdk:17-alpine

# Install curl if not already available
RUN apk add --no-cache curl

WORKDIR /app

# Download the .jar file from Nexus
RUN curl -o app.jar -L "http://192.168.33.10:8081/#browse/browse:DevDynamos:com%2Fbezkoder%2FDevDynamos%2F115%2FDevDynamos-115.jar"
#COPY target/spring-boot-security-jwt-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8090

# Run the jar file
CMD ["java", "-jar", "app.jar"]

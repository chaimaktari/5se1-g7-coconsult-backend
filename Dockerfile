FROM openjdk:17-alpine

RUN apk add --no-cache curl

WORKDIR /app

RUN curl -o app.jar -L "192.168.80.133:8081/repository/maven-releases/com/bezkoder/CoConsult/1.0.0/CoConsult-1.0.0.jar"

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]

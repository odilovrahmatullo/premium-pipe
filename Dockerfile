FROM openjdk:21-jdk-slim

# Yaratilgan JAR faylni konteynerga nusxalash
COPY libs/premium-pipe-0.0.1-SNAPSHOT.jar app.jar

# Spring Boot dasturini ishga tushirish
ENTRYPOINT ["java", "-jar", "/app.jar"]

EXPOSE 8080

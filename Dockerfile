# Java 21 JDK bilan Alpine Linux asosida engil Docker image
FROM eclipse-temurin:21-jdk-alpine

# Ilovani ishga tushirish uchun ishlatiladigan ishchi katalog
WORKDIR /app

# JAR faylni container ichiga nusxalash
COPY libs/premium-pipe.jar app.jar

# Spring Boot 8080 portda ishlaydi
EXPOSE 8080

# Java ilovasini ishga tushirish
CMD ["java", "-jar", "app.jar"]

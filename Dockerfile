# Java 21 JDK bilan Alpine Linux asosida engil Docker image
FROM eclipse-temurin:21-jdk-alpine

# Ilovani ishga tushirish uchun ishlatiladigan ishchi katalog
WORKDIR /app

# JAR faylni container ichiga nusxalash
COPY myjar/premium-pipe.jar app.jar

# Yuklangan fayllar uchun uploads papkasini yaratish
RUN mkdir -p /app/uploads

# Spring Boot 8080 portda ishlaydi
EXPOSE 8080

# Java ilovasini ishga tushirish
CMD ["java", "-jar", "app.jar"]

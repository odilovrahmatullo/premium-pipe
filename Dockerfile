# Java 21 bilan Alpine bazaviy imij
FROM eclipse-temurin:21-jdk-alpine

# Ishchi katalogni yaratish
WORKDIR /app

# build.gradle va settings.gradle fayllarni ko'chirib olish
COPY build.gradle settings.gradle gradlew ./
COPY gradle gradle

# Gradle wrapper'ni bajariladigan qilish
RUN chmod +x gradlew

# Gradle orqali dependency'larni yuklash (caching uchun)
COPY src src
RUN ./gradlew build --no-daemon

# Yaratilgan JAR faylni nusxalash
COPY build/libs/*.jar app.jar

# Portni ochish
EXPOSE 8080

# Ilovani ishga tushirish
CMD ["java", "-jar", "app.jar"]

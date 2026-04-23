FROM eclipse-temurin:17-jre

WORKDIR /app

COPY target/projektet_a_secure_webshop-1.2.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
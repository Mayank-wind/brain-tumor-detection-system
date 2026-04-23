FROM maven:3.9.11-eclipse-temurin-25 AS build

WORKDIR /app

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw

COPY src/ src/
RUN ./mvnw -DskipTests package

FROM eclipse-temurin:25-jre

WORKDIR /app

COPY --from=build /app/target/brain.tumor.backend-0.0.1-SNAPSHOT.jar app.jar

COPY ai-model /app/ai-model

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

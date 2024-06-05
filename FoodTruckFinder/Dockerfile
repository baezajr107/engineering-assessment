# Build stage
FROM maven:3.9.7-eclipse-temurin-17 AS build
COPY pom.xml /app/
COPY src /app/src
RUN mvn -f /app/pom.xml clean package

# Run stage
FROM openjdk:17-jdk-alpine
COPY --from=build /app/target/FoodTruckFinder-1.0-SNAPSHOT.jar /app/FoodTruckFinder-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/app/FoodTruckFinder-1.0-SNAPSHOT.jar"]
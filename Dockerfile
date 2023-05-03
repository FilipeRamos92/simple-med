# Stage 1
FROM openjdk:8-jdk-alpine as builder

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

COPY src ./src

RUN ./mvnw clean install -DskipTests

# Stage 2
FROM openjdk:8-jdk-alpine

WORKDIR /app

COPY --from=builder /app/target/*.jar /app/*.jar

ENTRYPOINT ["java", "-jar", "/app/*.jar"]
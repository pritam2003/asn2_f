# Start with a base image containing Java runtime
FROM maven:3.8.5-openjdk17 AS build
COPY . .
RUN mvn clean package -DskipTests




# Make port 8080 available to the world outside this container

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/asn2-0.0.1-SNAPSHOT.jar assignment.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "assignment.jar"] 

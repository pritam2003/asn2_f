# Start with a base image containing Java runtime
FROM maven:3.8.5-openjdk17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-

# Add Maintainer Info
LABEL maintainer="pda49@sfu.ca"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_FILE=target/asn2_f-0.0.1-SNAPSHOT.jar 

# Add the application's jar to the container
ADD ${JAR_FILE} asn2_f.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/asn2_f.jar"]

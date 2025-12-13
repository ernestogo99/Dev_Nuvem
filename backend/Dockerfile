
# Stage 1: Build the Java application
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy necessary Maven files first. 
COPY pom.xml .

# Copy all source code
COPY src /app/src

# Compile and package the application into a runnable JAR file
RUN mvn clean package -DskipTests


# Stage 2: Create the final, smaller runtime image
FROM openjdk:21-jre-slim 
WORKDIR /app

# Copy ONLY the compiled JAR file from the 'build' stage and rename it to app.jar
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar /app/app.jar 

# Expose the port your Spring Boot application runs on
EXPOSE 8080

# Command to run the application when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]
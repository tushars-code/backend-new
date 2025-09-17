# Step 1: Use an official OpenJDK image
FROM eclipse-temurin:17-jdk-jammy AS build

# Step 2: Set working directory inside container
WORKDIR /app

# Step 3: Copy Maven wrapper and pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Step 4: Download dependencies (so Docker cache can reuse this layer)
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Step 5: Copy the source code
COPY src src

# Step 6: Build the JAR
RUN ./mvnw -DskipTests package

# Step 7: Run with JDK slim image
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copy the JAR built in the previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose port 8080
EXPOSE 8080

# Start the app
ENTRYPOINT ["java", "-jar", "app.jar"]

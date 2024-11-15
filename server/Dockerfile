# Use official OpenJDK image
FROM openjdk:17-jdk

# Set working directory (опционально)
WORKDIR /app

# Copy JAR file
COPY target/server-1.0-SNAPSHOT.jar server.jar

# Expose the port your server listens on (например, 8080)
EXPOSE 8080

# Run server
ENTRYPOINT ["java", "-jar", "server.jar"]

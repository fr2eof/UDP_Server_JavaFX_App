
# Use official OpenJDK image
FROM openjdk:17-jdk

# Copy JAR file
COPY target/server.jar /app/server.jar

# Run server
ENTRYPOINT ["java", "-jar", "/app/server.jar"]

# # Step 1: Use the official Eclipse Temurin 21 (Java 21) JDK image as the base
# FROM eclipse-temurin:21-jdk AS build
#
# # Step 2: Set the working directory in the container
# WORKDIR /app
#
# # Step 3: Copy the Maven wrapper files and the pom.xml (this will allow caching of dependencies layer)
# COPY .mvn .mvn
# COPY mvnw mvnw
# COPY pom.xml pom.xml
#
# # Step 4: Make the mvnw script executable
# RUN chmod +x mvnw
#
# # Step 5: Download dependencies (this step will be cached if pom.xml doesn't change)
# RUN ./mvnw clean install -DskipTests
#
# # Step 6: Copy the rest of your application source code
# COPY src src
#
# # Step 7: Build the application (this step will re-run only when there are changes in your source files)
# RUN ./mvnw package -DskipTests
#
# # Check if the JAR exists
# RUN ls -al /app/target
#
# # Step 8: Create a new image for running the app (production stage)
# FROM eclipse-temurin:21-jdk AS runtime
#
# # Step 9: Set the working directory for the runtime container
# WORKDIR /app
#
# # Step 10: Copy the built JAR file from the build image to the runtime image
# COPY --from=build /app/target/invoice-management-0.0.1-SNAPSHOT.jar /app/invoice-management.jar
#
# # Step 11: Expose the port your app runs on (use the actual port if different)
# EXPOSE 8080
#
# # Step 12: Define the command to run the JAR file
# CMD ["java", "-jar", "invoice-management.jar"]



#========================================= After YT Video =========================================
FROM openjdk:21
EXPOSE 8080
ADD target/invoice-management.jar invoice-management.jar
ENTRYPOINT ["java", "-jar", "/invoice-management.jar"]
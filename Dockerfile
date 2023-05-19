FROM openjdk:17
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} app.jar
# Set the command to run the application
ENV PROJECT_NAME=your-project-name
ENTRYPOINT ["java", "-jar", "build/libs/$PROJECT_NAME.jar"]
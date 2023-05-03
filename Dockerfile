#dockerfile
FROM openjdk:17
CMD ["./mvnw", "clean", "package"]
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} libkiosk.jar
ENTRYPOINT ["java","-jar","/libkiosk.jar"]
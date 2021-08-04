FROM openjdk:8-jdk-alpine
EXPOSE 8080
ARG JAR_FILE=target/url-service-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} url-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/url-service-0.0.1-SNAPSHOT.jar"]
FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} seresco.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/seresco.jar"]
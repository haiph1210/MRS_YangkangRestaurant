FROM openjdk:8-jdk-alpine
COPY ./eureka-service/target/eureka-service-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java","-jar","-Xmx512m","-Dspring.profiles.active=test","eureka-service-0.0.1-SNAPSHOT.jar"]
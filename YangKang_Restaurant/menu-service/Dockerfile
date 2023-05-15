FROM openjdk:17-jdk-alpine
COPY ./target/menu-service-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-Deureka.client.serviceUrl.defaultZone=http://eureka:8761/eureka","-jar","-Xmx384m","-Dspring.profiles.active=test","menu-service-0.0.1-SNAPSHOT.jar"]
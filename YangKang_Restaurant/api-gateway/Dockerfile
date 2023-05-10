FROM openjdk:8-jdk-alpine
COPY /api-gateway/target/api-gateway-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-Deureka.client.serviceUrl.defaultZone=http://eureka-0.eureka.default.svc.cluster.local:8761/eureka","-jar","-Xmx384m","-Dspring.profiles.active=test","api-gateway-0.0.1-SNAPSHOT.jar"]
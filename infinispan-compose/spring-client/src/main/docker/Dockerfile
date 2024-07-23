FROM registry.access.redhat.com/ubi8/openjdk-17:1.13-1
USER root
RUN mkdir /app
COPY target/spring-client-1.0-SNAPSHOT.jar  /app
WORKDIR /app
CMD ["java", "-jar" , "spring-client-1.0-SNAPSHOT.jar"]


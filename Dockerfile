FROM openjdk:16
WORKDIR /
ADD target/check-0.0.1-SNAPSHOT.jar //
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/check-0.0.1-SNAPSHOT.jar"]
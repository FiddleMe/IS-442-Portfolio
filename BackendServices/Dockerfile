

FROM eclipse-temurin:17
VOLUME /tmp
ARG JAR_FILE

WORKDIR /usr/src/app
COPY target/BackendServices-0.0.1-SNAPSHOT.jar /app/backend.jar
ENTRYPOINT ["java", "-jar", "/app/backend.jar"]

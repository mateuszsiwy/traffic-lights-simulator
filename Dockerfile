FROM openjdk:21-jdk-slim

WORKDIR /app

COPY backend /app/backend

WORKDIR /app/backend
RUN rm -rf target

RUN ./mvnw clean package

COPY backend/target/traffic-lights-simulator-0.0.1-SNAPSHOT.jar /app/traffic-lights-simulator-0.0.1-SNAPSHOT.jar

ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/traffic-lights-simulator-0.0.1-SNAPSHOT.jar"]
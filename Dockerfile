FROM openjdk:21-jdk-slim AS build
WORKDIR /app
COPY backend /app
WORKDIR /app
RUN chmod +x mvnw && ./mvnw clean package --no-transfer-progress

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/target/traffic-lights-simulator-0.0.1-SNAPSHOT.jar /app/traffic-lights-simulator-0.0.1-SNAPSHOT.jar
ENV JAVA_OPTS=""
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/traffic-lights-simulator-0.0.1-SNAPSHOT.jar"]

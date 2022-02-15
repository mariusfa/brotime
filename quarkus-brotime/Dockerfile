FROM maven:3.8.4-openjdk-11-slim as builder

WORKDIR /app
COPY . .
RUN mvn package -DskipTests -Dquarkus.package.type=uber-jar

FROM openjdk:11-jre-slim

WORKDIR /app
COPY --from=builder /app/target/quarkus-brotime-1.0.0-SNAPSHOT-runner.jar .

CMD ["java", "-jar", "quarkus-brotime-1.0.0-SNAPSHOT-runner.jar"]
FROM maven:3.6.2-jdk-11 AS builder

WORKDIR /opt/app

COPY . .

RUN mvn clean install

FROM openjdk:11-jre-slim

RUN apt update && apt install -y \
    curl \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /opt/app

COPY --from=builder /opt/app/target/*.jar /opt/app/app.jar

HEALTHCHECK --interval=10s CMD curl --fail http://localhost:8080/api/health || exit 1

CMD [ "java", "-jar", "app.jar" ]
FROM openjdk:11-jdk-slim AS build
WORKDIR /CrackHashWorker
COPY . /CrackHashWorker
RUN ./gradlew bootJar

WORKDIR /
ENTRYPOINT ["java","-jar","/CrackHashWorker/build/libs/CrackHashWorker.jar"]

FROM openjdk:11-jdk-slim AS build
WORKDIR /CrackHashManager
COPY . /CrackHashManager
RUN ./gradlew bootJar

WORKDIR /
ENTRYPOINT ["java","-jar","/CrackHashManager/build/libs/CrackHashManager.jar"]
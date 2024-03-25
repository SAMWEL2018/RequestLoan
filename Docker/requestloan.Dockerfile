FROM eclipse-temurin:17-jdk-alpine

ENV VERSION 1.0.0

WORKDIR /app/RequestLoan/

ADD RequestLoan-$VERSION.jar $VERSION.jar

EXPOSE 8013

ENTRYPOINT ["java","-jar","1.0.0.jar", "--server"]
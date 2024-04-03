FROM maven:3.8.4-openjdk-17 as build

WORKDIR /build

COPY .mvn/ ./.mvn

COPY pom.xml  ./

COPY . .

RUN mvn package -DskipTests

FROM eclipse-temurin:17-jdk-alpine

RUN apk update && apk add curl

WORKDIR /app

COPY --from=build /build/target/*.jar run.jar

CMD java -jar /app/run.jar

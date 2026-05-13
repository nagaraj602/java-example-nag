FROM alpine/git AS git-clone
WORKDIR /git
COPY . .

FROM maven:3.9.15-amazoncorretto-25-debian-trixie AS build
WORKDIR /build
COPY --from=git-clone /git .
RUN mvn clean package

FROM amazoncorretto:25.0.3-al2023-headless
WORKDIR /app
COPY --from=build /build/target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]

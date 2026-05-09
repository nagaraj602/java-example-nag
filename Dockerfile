FROM maven:4.0.0-rc-5-ibm-semeru-25-noble

WORKDIR /app

COPY . .

RUN mvn clean package

CMD ["java", "-jar", "target/demo-java-example-demo-1.0.0.jar"]

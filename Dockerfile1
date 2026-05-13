FROM maven:4.0.0-rc-5-ibm-semeru-25-noble

# EXPOSE will just tell you that application needs 8080 port to be mapped. 
# It won't open the port, nor it give any warning while running the container. Only works with: docker run -d -p 8085:8085
# Only if we use docker inspect command, we will see in config file that we need to map 8085.
EXPOSE 8085

WORKDIR /app

COPY . .

RUN mvn clean package

CMD ["java", "-jar", "target/demo-java-example-demo-1.0.0.jar"]

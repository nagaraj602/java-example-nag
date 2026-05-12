# java-example-nag-jar   
└──> source code for building jar application


# 📦 Simple Spring Boot Application (Java 21+)

This is a minimal Spring Boot application that:

* Uses Java 21+
* Runs on port **8085**
* Can be built using **Maven** or **Gradle**
* Can be packaged into a **Docker image**
* Can be deployed to **Kubernetes**

---

# 📁 Project Structure

```
java-example-nag-jar/
 ├── src/main/java/com/example/demo/DemoApplication.java
 ├── src/main/resources/application.yml
 ├── pom.xml
 ├── build.gradle
 ├── settings.gradle
 ├── Dockerfile
 └── k8s-deployment.yaml
```

---

# ☕ Java Code

## DemoApplication.java

```java
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@SpringBootApplication
@RestController
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @GetMapping("/")
    public String home() {

        String publicIp = "Unknown";

        try {
            Process process = Runtime.getRuntime().exec("curl -s ifconfig.me");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );

            publicIp = reader.readLine();

        } catch (Exception e) {
            publicIp = "Error fetching IP";
        }

        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<title>Spring Boot App</title>" +
                "<style>" +

                "body {" +
                "margin:0;" +
                "height:100vh;" +
                "display:flex;" +
                "justify-content:center;" +
                "align-items:center;" +
                "background: linear-gradient(-45deg,#0f0f0f,#1a1a2e,#16213e,#0f3460);" +
                "background-size:400% 400%;" +
                "animation: gradientBG 10s ease infinite;" +
                "font-family:Arial;" +
                "color:white;" +
                "text-align:center;" +
                "}" +

                ".card {" +
                "padding:40px 60px;" +
                "border-radius:20px;" +
                "background:rgba(255,255,255,0.05);" +
                "backdrop-filter:blur(10px);" +
                "box-shadow:0 0 30px rgba(0,0,0,0.6);" +
                "}" +

                "h1 {" +
                "font-size:60px;" +
                "font-weight:900;" +
                "margin:0;" +
                "}" +

                "p {" +
                "font-size:22px;" +
                "font-weight:600;" +
                "}" +

                "@keyframes gradientBG {" +
                "0%{background-position:0% 50%;}" +
                "50%{background-position:100% 50%;}" +
                "100%{background-position:0% 50%;}" +
                "}" +

                "</style>" +
                "</head>" +

                "<body>" +
                "<div class='card'>" +
                "<h1>My Spring Boot Java App Running 🚀</h1>" +
                "<p>Public IP: " + publicIp + "</p>" +
                "<p>Port: 8085</p>" +
                "</div>" +
                "</body>" +
                "</html>";
    }
}
```

---

# ⚙️ application.yml

```yaml
server:
  port: 8085
```

---

# 📦 pom.xml (Maven)

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://www.m...">

    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>demo</artifactId>
    <version>java-example-demo-1.0.0</version>
    <name>demo</name>
    <description>Simple Spring Boot App</description>

    <properties>
        <java.version>21</java.version>
        <spring.boot.version>3.3.0</spring.boot.version>
    </properties>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${spring.boot.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>${spring.boot.version}</version>
            </plugin>
        </plugins>
    </build>

</project>
```

---

# ⚙️ build.gradle (Gradle)

```groovy
plugins {
    id 'java'
    id 'org.springframework.boot' version '3.3.0'
    id 'io.spring.dependency-management' version '1.1.6'
}

group = 'com.example'
version = 'java-example-demo-1.0.0'

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
}


tasks.named('test') {
    useJUnitPlatform()
}
```

## settings.gradle

```groovy
rootProject.name = 'demo'
```

---

# 🐳 Dockerfile

```dockerfile
FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/demo-java-example-demo-1.0.0.jar app.jar

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

# ☸️ Kubernetes YAML

## k8s-deployment.yaml

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: spring-boot-app
spec:
  replicas: 1
  selector:
    matchLabels:
      app: spring-boot-app
  template:
    metadata:
      labels:
        app: spring-boot-app
    spec:
      containers:
        - name: spring-boot-container
          image: your-dockerhub-username/demo:latest
          ports:
            - containerPort: 8085
---
apiVersion: v1
kind: Service
metadata:
  name: spring-boot-service
spec:
  type: NodePort
  selector:
    app: spring-boot-app
  ports:
    - port: 80
      targetPort: 8085
      nodePort: 30007
```

---

# 🚀 How to Use

## Build using Maven

```bash
mvn clean install
```

Run the JAR
```bash
java -jar target/demo-java-example-demo-1.0.0.jar
```

## Build using Gradle
```bash
gradle clean build
```

Run the JAR
```bash
java -jar build/libs/demo-java-example-demo-1.0.0.jar
```

## Build Docker Image

```bash
docker build -t your-dockerhub-username/demo:latest .
```

## Deploy to Kubernetes

```bash
kubectl apply -f k8s-deployment.yaml
```

---

# ✅ Output

Open browser:

```
http://<server_IP>:8085
```

You should see:

```
Hello, Spring Boot is running on port 8085!
```

---


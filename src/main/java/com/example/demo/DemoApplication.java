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
            e.printStackTrace();
        }

        return "Hello, Spring Boot is running on " + publicIp + " port 8085!";
    }
}

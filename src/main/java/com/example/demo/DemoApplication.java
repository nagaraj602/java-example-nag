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
                "<h1>Spring Boot Running 🚀</h1>" +
                "<p>Public IP: " + publicIp + "</p>" +
                "<p>Port: 8085</p>" +
                "</div>" +
                "</body>" +
                "</html>";
    }
}

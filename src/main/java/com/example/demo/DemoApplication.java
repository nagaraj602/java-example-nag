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

    return """
    <!DOCTYPE html>
    <html>
    <head>
        <title>Spring Boot App</title>

        <style>
            body {
                margin: 0;
                height: 100vh;
                display: flex;
                justify-content: center;
                align-items: center;
                background: linear-gradient(-45deg, #0f0f0f, #1a1a2e, #16213e, #0f3460);
                background-size: 400% 400%;
                animation: gradientBG 10s ease infinite;
                font-family: Arial, sans-serif;
                color: white;
                text-align: center;
            }

            h1 {
                font-size: 50px;
                font-weight: bold;
                text-shadow: 2px 2px 20px rgba(0,0,0,0.8);
            }

            p {
                font-size: 24px;
                font-weight: 600;
                margin-top: 20px;
            }

            @keyframes gradientBG {
                0% { background-position: 0% 50%; }
                50% { background-position: 100% 50%; }
                100% { background-position: 0% 50%; }
            }
        </style>
    </head>

    <body>
        <div>
            <h1>Spring Boot is Running 🚀</h1>
            <p>Server Public IP: """ + publicIp + """</p>
            <p>Port: 8085</p>
        </div>
    </body>
    </html>
    """;
}

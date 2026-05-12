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
        }

        .card {
            text-align: center;
            padding: 40px 60px;
            border-radius: 20px;
            background: rgba(255, 255, 255, 0.05);
            backdrop-filter: blur(12px);
            box-shadow: 0 0 30px rgba(0,0,0,0.6);
            animation: floatCard 4s ease-in-out infinite;
        }

        h1 {
            font-size: 60px;
            font-weight: 900;
            margin: 0;
            text-transform: uppercase;
            letter-spacing: 2px;
            animation: glowText 2s ease-in-out infinite alternate;
        }

        p {
            font-size: 22px;
            font-weight: 600;
            margin-top: 15px;
            opacity: 0.9;
        }

        .ip {
            font-size: 20px;
            margin-top: 10px;
            color: #00ffcc;
        }

        @keyframes gradientBG {
            0% { background-position: 0% 50%; }
            50% { background-position: 100% 50%; }
            100% { background-position: 0% 50%; }
        }

        @keyframes glowText {
            from {
                text-shadow: 0 0 10px #00ffcc, 0 0 20px #00ffcc;
            }
            to {
                text-shadow: 0 0 25px #00ffff, 0 0 50px #00ffcc;
            }
        }

        @keyframes floatCard {
            0% { transform: translateY(0px); }
            50% { transform: translateY(-10px); }
            100% { transform: translateY(0px); }
        }
    </style>
</head>

<body>
    <div class="card">
        <h1>Spring Boot Running</h1>
        <p>Server is live and healthy 🚀</p>
        <div class="ip">Public IP: """ + publicIp + """</div>
        <p>Port: 8085</p>
        </div>
    </body>
    </html>
    """;
}

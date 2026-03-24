package com.brain.tumor.controller;

import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatController {

    @PostMapping
    public String chat(@RequestBody Map<String,Object> request) {
        try {

            String prediction = (String) request.get("prediction");
            Double confidence = Double.parseDouble(request.get("confidence").toString());
            String question = (String) request.get("question");

            if (prediction == null || prediction.isEmpty()) {
                return "⚠️ Please run prediction first.";
            }

            String prompt;

            if (question != null && !question.isEmpty()) {

                // 🔥 User asked something
                prompt = "A patient has been diagnosed with " + prediction +
                        " with " + (confidence * 100) + "% confidence.\n\n" +

                        "User question: " + question + "\n\n" +

                        "Answer clearly in simple language. Do NOT prescribe medicine.";

            } else {

                // 🔥 Default explanation
                prompt = "A patient has been diagnosed with " + prediction +
                        " with " + (confidence * 100) + "% confidence.\n\n" +

                        "Explain:\n" +
                        "1. What this means\n" +
                        "2. Precautions\n" +
                        "3. Lifestyle advice\n" +
                        "4. When to consult a doctor\n\n" +

                        "Keep it simple and safe.";
            }

            URL url = new URL("http://localhost:11434/api/generate");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");

            String safePrompt = prompt
                    .replace("\"", "\\\"")
                    .replace("\n", "\\n");

            String body = "{\n" +
                    "  \"model\": \"llama3\",\n" +
                    "  \"prompt\": \"" + safePrompt + "\",\n" +
                    "  \"stream\": false\n" +
                    "}";

            System.out.println("Sending request to Ollama:");
            System.out.println(body);

            OutputStream os = conn.getOutputStream();
            os.write(body.getBytes());
            os.flush();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            String fullResponse = response.toString();

            String aiText = "";

            if (fullResponse.contains("\"response\"")) {
                aiText = fullResponse.split("\"response\":\"")[1].split("\"")[0];

                // 🔥 CLEAN TEXT
                aiText = aiText.replace("\\n", "\n");   // fix new lines
                aiText = aiText.replace("**", "");      // remove bold markdown
                aiText = aiText.replace("\\t", " ");    // remove tabs
            }

            return aiText;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error 🤦‍♂️ or may be AI service not running. Please start Ollama👺.";
        }
    }
}
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
                prompt = "A brain MRI scan result shows: " + prediction +
                        " with confidence " + (confidence * 100) + "%.\n\n" +

                        "Conversation:\n" + question + "\n\n" +

                        "Answer the user's latest question clearly.\n" +
                        "Also consider:\n" +
                        "- Possible tumor type\n" +
                        "- Severity level\n" +
                        "- Simple explanation\n" +
                        "- Precautions\n\n" +

                        "Do NOT prescribe medicine.";

            } else {
                prompt = "A brain MRI scan result shows: " + prediction +
                        " with confidence " + (confidence * 100) + "%.\n\n" +

                        "Explain in simple medical terms:\n" +
                        "1. Possible tumor type (if tumor)\n" +
                        "2. Severity level (low / medium / high)\n" +
                        "3. General idea of tumor size (small / medium / large)\n" +
                        "4. What this means for the patient\n" +
                        "5. Precautions and next steps\n\n" +

                        "Keep it simple, safe, and informative.";
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
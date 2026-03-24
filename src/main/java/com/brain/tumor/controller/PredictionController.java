package com.brain.tumor.controller;
import com.brain.tumor.dto.PredictionResponseDTO;
import com.brain.tumor.model.Prediction;
import com.brain.tumor.repository.PredictionRepository;
import com.brain.tumor.service.PredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // allow frontend
public class PredictionController {

    @Autowired
    private PredictionService predictionService;

    @Autowired
    private PredictionRepository predictionRepository;

    @PostMapping("/predict")
    public Map<String, Object> predict(@RequestParam("file") MultipartFile file){
        try {

            File uploadDir = new File("P:/temp/uploads");

            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            File dest = new File(uploadDir, file.getOriginalFilename());
            file.transferTo(dest);

            String filePath = dest.getAbsolutePath();

            System.out.println("Saved file at: " + filePath);

            ProcessBuilder pb = new ProcessBuilder(
                    "C:\\Users\\Mayank\\AppData\\Local\\Programs\\Python\\Python313\\python.exe",
                    "ai-model/test_model.py",
                    filePath
            );

            pb.directory(new File("P:/Users/Mayank/IdeaProjects/brain.tumor.backend"));

            Process process = pb.start();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );

            BufferedReader errorReader = new BufferedReader(
                    new InputStreamReader(process.getErrorStream())
            );

            String line;
            while ((line = errorReader.readLine()) != null) {
                System.out.println("PYTHON ERROR: " + line);
            }

            String result = "";
            while ((line = reader.readLine()) != null) {
                System.out.println("PYTHON OUTPUT: " + line);
                result = line;
            }

            String output = result.trim();

            String[] parts = output.split(":");

            if (parts.length < 2) {
                Map<String, Object> error = new HashMap<>();
                error.put("error", "Invalid model output");
                return error;
            }

            String prediction = parts[0];
            double confidence = Double.parseDouble(parts[1]);

            Map<String, Object> response = new HashMap<>();
            response.put("prediction", prediction);
            response.put("confidence", confidence);

            Prediction p = new Prediction();
            p.setImageName(file.getOriginalFilename());
            p.setPrediction(prediction);
            p.setConfidence(confidence);
            p.setTimestamp(LocalDateTime.now());

            predictionRepository.save(p);

            return response;

        } catch (Exception e) {
            e.printStackTrace();

            Map<String, Object> response = new HashMap<>();
            response.put("error", "Something went wrong");
            return response;
        }
    }

    @GetMapping("/history")
    public List<Prediction> getHistory() {
        return predictionRepository
                .findAll(Sort.by(Sort.Direction.DESC, "timestamp"))
                .stream()
                .limit(5)
                .toList();
    }
}
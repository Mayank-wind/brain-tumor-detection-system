package com.brain.tumor.controller;
import com.brain.tumor.dto.PredictionResponseDTO;
import com.brain.tumor.service.PredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // allow frontend
public class PredictionController {

    @Autowired
    private PredictionService predictionService;

    @PostMapping("/predict")
    public String predict(@RequestParam("file") MultipartFile file) {
        try {

            File uploadDir = new File(System.getProperty("user.dir") + "/ai-model");

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

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }
}
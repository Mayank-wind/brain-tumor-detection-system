package com.brain.tumor.controller;
import com.brain.tumor.dto.PredictionResponseDTO;
import com.brain.tumor.service.PredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // allow frontend
public class PredictionController {

    @Autowired
    private PredictionService predictionService;

    @PostMapping("/predict")
    public ResponseEntity<PredictionResponseDTO> predict(
            @RequestParam("file") MultipartFile file) {
        PredictionResponseDTO response = predictionService.getPrediction(file);
        return ResponseEntity.ok(response);
    }
}
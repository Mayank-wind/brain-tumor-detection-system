package com.brain.tumor.service;
import com.brain.tumor.dto.PredictionResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

    @Service
    public class PredictionService {
        public PredictionResponseDTO getPrediction(MultipartFile file) {
            return new PredictionResponseDTO(
                    "Tumor Detected",
                    0.92,
                    "Glioma"
            );
        }
    }


package com.brain.tumor.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PredictionResponseDTO {
    private String result;
    private double confidence;
    private String tumorType;
}
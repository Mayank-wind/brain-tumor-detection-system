package com.brain.tumor.service;
import com.brain.tumor.dto.PredictionResponseDTO;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
@Service
public class PredictionService {

    @Value("${app.python-api-url}")
    private String pythonApiUrl;

    public PredictionResponseDTO getPrediction(MultipartFile file) {

        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            ByteArrayResource resource = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            };

            body.add("file", resource);

            HttpEntity<MultiValueMap<String, Object>> requestEntity =
                    new HttpEntity<>(body, headers);

            ResponseEntity<PredictionResponseDTO> response =
                    restTemplate.postForEntity(
                            pythonApiUrl,
                            requestEntity,
                            PredictionResponseDTO.class
                    );

            return response.getBody();

        } catch (Exception e) {
            e.printStackTrace();
            return new PredictionResponseDTO("Error", 0.0, "None");
        }
    }
}

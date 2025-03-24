package com.example.homies.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AiService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String pythonApiUrl = "http://localhost:5000/process_image";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<String> processImage(String imagePath, int sampleCount, String inputText) {
        try {
            // Resolve absolute path
            String absoluteImagePath = Paths.get(imagePath).toAbsolutePath().toString();

            // Create the request payload as a Map
            Map<String, Object> payload = Map.of(
                    "image_path", absoluteImagePath,
                    "sample_count", sampleCount,
                    "input_text", inputText
            );
            // Set headers using Spring's HttpHeaders
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create HTTP entity
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

            // Make the POST request
            ResponseEntity<String> response = restTemplate.exchange(
                    pythonApiUrl,
                    HttpMethod.POST,
                    request,
                    String.class
            );

            // Parse the response using Jackson
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            List<String> sampleImages = new ArrayList<>();
            jsonNode.get("sample_images").forEach(imageNode -> sampleImages.add(imageNode.asText()));

            return sampleImages;
        } catch (Exception e) {
            throw new RuntimeException("Failed to process image", e);
        }
    }
}

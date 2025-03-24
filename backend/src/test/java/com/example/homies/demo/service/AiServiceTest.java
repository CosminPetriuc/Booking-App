package com.example.homies.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AiServiceTest {

    @InjectMocks
    private AiService aiService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper objectMapper;

    private final String pythonApiUrl = "http://localhost:5000/process_image";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessImage_WithSpecificPaths() throws Exception {
        // Arrange
        String imagePath1 = "ML-DS/Data/generated_images_Amsterdam/_historic_centre___bright___canal_view___jordaan__front.png";
        String imagePath2 = "ML-DS/Data/generated_images_Amsterdam/_historic_centre___bright___canal_view___jordaan__inside.png";
        int sampleCount = 2;
        String inputText = "Describe these images";

        // Mock JSON response
        String jsonResponse = "{ \"sample_images\": [\"image1_processed.png\", \"image2_processed.png\"] }";
        when(restTemplate.exchange(
                eq(pythonApiUrl),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(String.class)
        )).thenReturn(ResponseEntity.ok(jsonResponse));
        when(objectMapper.readTree(jsonResponse)).thenReturn(new ObjectMapper().readTree(jsonResponse));

        // Act
        List<String> result1 = aiService.processImage(imagePath1,sampleCount, inputText);
        List<String> result2 = aiService.processImage(imagePath2,sampleCount, inputText);

        // Assert
        assertNotNull(result1);
        assertEquals(2, result1.size());
        assertTrue(result1.contains("image1_processed.png"));

        assertNotNull(result2);
        assertEquals(2, result2.size());
        assertTrue(result2.contains("image2_processed.png"));

        // Verify headers and request payload
        ArgumentCaptor<HttpEntity<Map<String, Object>>> captor = ArgumentCaptor.forClass(HttpEntity.class);
        verify(restTemplate, times(2)).exchange(eq(pythonApiUrl), eq(HttpMethod.POST), captor.capture(), eq(String.class));

        List<HttpEntity<Map<String, Object>>> capturedRequests = captor.getAllValues();
        assertEquals(imagePath1, capturedRequests.get(0).getBody().get("image_path"));
        assertEquals(imagePath2, capturedRequests.get(1).getBody().get("image_path"));
        assertEquals(inputText, capturedRequests.get(0).getBody().get("input_text"));
        assertEquals(inputText, capturedRequests.get(1).getBody().get("input_text"));
    }


    @Test
    void testProcessImage_Failure() {
        // Arrange
        String imagePath = "images/nonexistent.jpg";
        String inputText = "Invalid input";
        int sampleCount = 1;

        when(restTemplate.exchange(
                eq(pythonApiUrl),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(String.class)
        )).thenThrow(new RuntimeException("Error processing image"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> aiService.processImage(imagePath, sampleCount,inputText));
        assertEquals("Failed to process image", exception.getMessage());
    }

    @Test
    void testProcessImage_InvalidResponseFormat() throws Exception {
        // Arrange
        String imagePath = "images/sample.jpg";
        String inputText = "Test input text";
        int sampleCount = 1;

        // Mock invalid JSON response
        String invalidJsonResponse = "{ \"unexpected_key\": \"unexpected_value\" }";
        when(restTemplate.exchange(
                eq(pythonApiUrl),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(String.class)
        )).thenReturn(ResponseEntity.ok(invalidJsonResponse));
        when(objectMapper.readTree(invalidJsonResponse)).thenThrow(new RuntimeException("Invalid response"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> aiService.processImage(imagePath,sampleCount, inputText));
        assertEquals("Failed to process image", exception.getMessage());
    }
}

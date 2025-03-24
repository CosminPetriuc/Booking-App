package com.example.homies.demo.controller;

import com.example.homies.demo.service.AiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/ai")
public class AiController {
    private final AiService aiService;

    public AiController(AiService aiService){
        this.aiService = aiService;
    }
    @PostMapping("/process-image")
    public ResponseEntity<List<String>> processImage(
            @RequestParam("imagePath") String imagePath,
            @RequestParam("sampleCount") int sampleCount,
            @RequestParam("inputText") String inputText
    ){
        try{
            List<String> sampleImages = aiService.processImage(imagePath,sampleCount,inputText);
            return new ResponseEntity<>(sampleImages, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

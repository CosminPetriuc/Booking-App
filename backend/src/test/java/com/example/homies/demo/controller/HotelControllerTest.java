package com.example.homies.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class HotelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testSearchAvailableHotels() throws Exception {
        mockMvc.perform(get("/api/hotel/available")
                        .param("city", "PARIS") // Replace with valid city enum
                        .param("startDate", "2024-01-01T10:00:00")
                        .param("endDate", "2024-01-05T10:00:00"))
                .andExpect(status().isOk());
    }
}

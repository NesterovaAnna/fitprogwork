package ru.omstu.fitprogwork.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.omstu.fitprogwork.dto.ExtractRequestDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DataExtractorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testJsonRequest() throws Exception {
        ExtractRequestDto request = new ExtractRequestDto();
        request.setType("json");
        request.setData("{\"name\":\"Иван Иванов\",\"relation\":[{\"name\":\"Петр\"},{\"name\":\"Анастасия\"}]}");
        request.setPath("/relation/1/name");

        mockMvc.perform(post("/api/data/extract")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value("Анастасия"));
    }

    @Test
    void testXmlRequest() throws Exception {
        ExtractRequestDto request = new ExtractRequestDto();
        request.setType("xml");
        request.setData("<root><name>Иван Иванов</name><relation><name>Петр</name></relation><relation><name>Анастасия</name></relation></root>");
        request.setPath("/relation/1/name");

        mockMvc.perform(post("/api/data/extract")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value("Анастасия"));
    }

    @Test
    void testErrorResponse() throws Exception {
        ExtractRequestDto request = new ExtractRequestDto();
        request.setType("json");
        request.setData("{\"name\":\"Иван\"}");
        request.setPath("/age");

        mockMvc.perform(post("/api/data/extract")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").exists());
    }
}
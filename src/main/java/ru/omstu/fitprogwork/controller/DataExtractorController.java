package ru.omstu.fitprogwork.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.omstu.fitprogwork.dto.ExtractRequestDto;
import ru.omstu.fitprogwork.service.DataProcessingService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/data")
public class DataExtractorController {

    @Autowired
    private DataProcessingService dataProcessingService;

    @PostMapping("/extract")
    public Map<String, String> extractData(@RequestBody ExtractRequestDto request) {
        Map<String, String> response = new HashMap<>();
        
        try {
            String value = dataProcessingService.extract(request);
            response.put("value", value);
        } catch (Exception e) {
            response.put("error", e.getMessage());
        }
        
        return response;
    }
}
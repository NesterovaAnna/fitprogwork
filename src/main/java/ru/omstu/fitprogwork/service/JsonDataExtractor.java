package ru.omstu.fitprogwork.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class JsonDataExtractor implements DataExtractorService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String extract(String data, String path) throws Exception {
        JsonNode root = objectMapper.readTree(data);
        String[] parts = path.split("/");
        
        JsonNode current = root;
        for (String part : parts) {
            if (part.isEmpty()) continue;
            
            if (part.matches("\\d+")) {
                int index = Integer.parseInt(part);
                current = current.get(index);
            } else {
                current = current.get(part);
            }
            
            if (current == null) {
                throw new Exception("Путь не найден: " + part);
            }
        }
        
        return current.asText();
    }

    @Override
    public String getType() {
        return "json";
    }
}
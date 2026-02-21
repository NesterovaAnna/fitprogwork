package ru.omstu.fitprogwork.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;

public class JsonDataProcessor implements DataProcessor {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String getValueByPath(String filePath, String fieldPath) {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
            if (inputStream == null) {
                return "Файл не найден: " + filePath;
            }

            JsonNode rootNode = objectMapper.readTree(inputStream);

            String path = fieldPath.startsWith("/") ? fieldPath.substring(1) : fieldPath;
            String[] parts = path.split("/");

            JsonNode currentNode = rootNode;
            for (String part : parts) {
                if (part.isEmpty()) continue;

                if (part.matches("\\d+")) {
                    int index = Integer.parseInt(part);
                    if (currentNode.isArray() && index < currentNode.size()) {
                        currentNode = currentNode.get(index);
                    } else {
                        return "Индекс вне границ массива: " + index;
                    }
                } else {
                    currentNode = currentNode.get(part);
                }

                if (currentNode == null) {
                    return "Путь не найден: " + part;
                }
            }

            return currentNode.asText();

        } catch (Exception e) {
            return "Ошибка при обработке JSON: " + e.getMessage();
        }
    }
}
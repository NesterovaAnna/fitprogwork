package ru.omstu.fitprogwork.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.omstu.fitprogwork.cache.DatabaseCacheService;
import ru.omstu.fitprogwork.dto.ExtractRequestDto;
import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataProcessingService {

    @Autowired
    private List<DataExtractorService> extractors;

    @Autowired
    private DatabaseCacheService cacheService;

    private Map<String, DataExtractorService> extractorMap = new HashMap<>();

    @PostConstruct
    public void init() {
        for (DataExtractorService extractor : extractors) {
            extractorMap.put(extractor.getType(), extractor);
        }
    }

    public String extract(ExtractRequestDto request) throws Exception {
        String type = request.getType().toLowerCase();
        
        DataExtractorService extractor = extractorMap.get(type);
        if (extractor == null) {
            throw new Exception("Неподдерживаемый тип: " + type);
        }

        String cacheKey = type + "|" + request.getData() + "|" + request.getPath();
        
        return cacheService.getOrCompute(cacheKey, () -> {
            try {
                return extractor.extract(request.getData(), request.getPath());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
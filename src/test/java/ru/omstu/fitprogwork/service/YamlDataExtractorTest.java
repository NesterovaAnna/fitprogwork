package ru.omstu.fitprogwork.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class YamlDataExtractorTest {

    private final YamlDataExtractor extractor = new YamlDataExtractor();

    @Test
    void extractNameFromYaml() throws Exception {
        String yaml = "name: Иван Иванов";
        String result = extractor.extract(yaml, "/name");
        assertEquals("Иван Иванов", result);
    }

    @Test
    void extractNestedValueFromYaml() throws Exception {
        String yaml = "relation:\n  - name: Петр\n  - name: Анастасия";
        String result = extractor.extract(yaml, "/relation/1/name");
        assertEquals("Анастасия", result);
    }
}
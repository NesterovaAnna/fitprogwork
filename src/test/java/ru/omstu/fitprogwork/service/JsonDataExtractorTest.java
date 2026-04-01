package ru.omstu.fitprogwork.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JsonDataExtractorTest {

    private final JsonDataExtractor extractor = new JsonDataExtractor();

    @Test
    void extractNameFromJson() throws Exception {
        String json = "{\"name\":\"Иван Иванов\"}";
        String result = extractor.extract(json, "/name");
        assertEquals("Иван Иванов", result);
    }

    @Test
    void extractNestedValueFromJson() throws Exception {
        String json = "{\"relation\":[{\"name\":\"Петр\"},{\"name\":\"Анастасия\"}]}";
        String result = extractor.extract(json, "/relation/1/name");
        assertEquals("Анастасия", result);
    }

    @Test
    void extractArrayElementFromJson() throws Exception {
        String json = "{\"skills\":[\"Java\",\"SQL\",\"Git\"]}";
        String result = extractor.extract(json, "/skills/0");
        assertEquals("Java", result);
    }

    @Test
    void pathNotFoundThrowsException() {
        String json = "{\"name\":\"Иван\"}";
        assertThrows(Exception.class, () -> extractor.extract(json, "/age"));
    }
}
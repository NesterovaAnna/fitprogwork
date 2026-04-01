package ru.omstu.fitprogwork.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.omstu.fitprogwork.dto.ExtractRequestDto;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DataProcessingServiceTest {

    @Autowired
    private DataProcessingService service;

    @Test
    void testJsonExtractor() throws Exception {
        ExtractRequestDto request = new ExtractRequestDto();
        request.setType("json");
        request.setData("{\"name\":\"Иван Иванов\",\"relation\":[{\"name\":\"Петр\"},{\"name\":\"Анастасия\"}]}");
        request.setPath("/relation/1/name");

        String result = service.extract(request);
        assertEquals("Анастасия", result);
    }

    @Test
    void testXmlExtractor() throws Exception {
        ExtractRequestDto request = new ExtractRequestDto();
        request.setType("xml");
        request.setData("<root><name>Иван Иванов</name><relation><name>Петр</name></relation><relation><name>Анастасия</name></relation></root>");
        request.setPath("/relation/1/name");

        String result = service.extract(request);
        assertEquals("Анастасия", result);
    }

    @Test
    void testYamlExtractor() throws Exception {
        ExtractRequestDto request = new ExtractRequestDto();
        request.setType("yaml");
        request.setData("name: Иван Иванов\nrelation:\n  - name: Петр\n  - name: Анастасия");
        request.setPath("/relation/1/name");

        String result = service.extract(request);
        assertEquals("Анастасия", result);
    }

    @Test
    void testUnsupportedType() {
        ExtractRequestDto request = new ExtractRequestDto();
        request.setType("unsupported");
        request.setData("{}");
        request.setPath("/test");

        assertThrows(Exception.class, () -> service.extract(request));
    }
}
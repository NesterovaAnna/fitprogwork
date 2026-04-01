package ru.omstu.fitprogwork.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class XmlDataExtractorTest {

    private final XmlDataExtractor extractor = new XmlDataExtractor();

    @Test
    void extractNameFromXml() throws Exception {
        String xml = "<root><name>Иван Иванов</name></root>";
        String result = extractor.extract(xml, "/name");
        assertEquals("Иван Иванов", result);
    }

    @Test
    void extractNestedValueFromXml() throws Exception {
        String xml = "<root><relation><name>Петр</name></relation><relation><name>Анастасия</name></relation></root>";
        String result = extractor.extract(xml, "/relation/1/name");
        assertEquals("Анастасия", result);
    }

    @Test
    void extractArrayElementFromXml() throws Exception {
        String xml = "<root><skills>Java</skills><skills>SQL</skills><skills>Git</skills></root>";
        String result = extractor.extract(xml, "/skills/0");
        assertEquals("Java", result);
    }
}
package ru.omstu.fitprogwork.config;

import ru.omstu.fitprogwork.processor.DataProcessor;
import ru.omstu.fitprogwork.processor.JsonDataProcessor;
import ru.omstu.fitprogwork.processor.XmlDataProcessor;

public class DataProcessorFactory {

    public static DataProcessor getProcessorByFileName(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("Имя файла не может быть пустым");
        }

        String lowerCase = fileName.toLowerCase();
        if (lowerCase.endsWith(".json")) {
            return new JsonDataProcessor();
        } else if (lowerCase.endsWith(".xml")) {
            return new XmlDataProcessor();
        } else {
            throw new IllegalArgumentException("Неподдерживаемый формат файла: " + fileName);
        }
    }
}

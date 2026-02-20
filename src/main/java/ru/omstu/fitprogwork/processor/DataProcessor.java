package ru.omstu.fitprogwork.processor;

public interface DataProcessor {
    /**
     * Получает значение по указанному пути из файла
     * @param filePath путь к файлу в resources
     * @param fieldPath путь к полю (например, "/name" или "/relation/1/name")
     * @return значение поля в виде строки
     */
    String getValueByPath(String filePath, String fieldPath);
}
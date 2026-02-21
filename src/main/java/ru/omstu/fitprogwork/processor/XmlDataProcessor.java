package ru.omstu.fitprogwork.processor;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XmlDataProcessor implements DataProcessor {

    @Override
    public String getValueByPath(String filePath, String fieldPath) {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
            if (inputStream == null) {
                return "Файл не найден: " + filePath;
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);
            document.getDocumentElement().normalize();

            String path = fieldPath.startsWith("/") ? fieldPath.substring(1) : fieldPath;
            String[] parts = path.split("/");

            // Начинаем с корневого элемента
            List<Element> currentElements = new ArrayList<>();
            currentElements.add(document.getDocumentElement());

            for (String part : parts) {
                if (part.isEmpty()) continue;

                List<Element> nextElements = new ArrayList<>();

                if (part.matches("\\d+")) {
                    // Индекс - берем элемент с этим индексом из текущего списка
                    int index = Integer.parseInt(part);
                    if (index < currentElements.size()) {
                        nextElements.add(currentElements.get(index));
                    } else {
                        return "Индекс " + index + " вне границ. Всего элементов: " + currentElements.size();
                    }
                } else {
                    // Имя тега - собираем ВСЕ дочерние элементы с этим именем
                    for (Element elem : currentElements) {
                        NodeList children = elem.getChildNodes();
                        for (int i = 0; i < children.getLength(); i++) {
                            Node child = children.item(i);
                            if (child.getNodeType() == Node.ELEMENT_NODE &&
                                    child.getNodeName().equals(part)) {
                                nextElements.add((Element) child);
                            }
                        }
                    }
                }

                if (nextElements.isEmpty()) {
                    return "Элемент не найден: " + part;
                }
                currentElements = nextElements;
            }

            // Возвращаем текст первого найденного элемента
            return currentElements.get(0).getTextContent();

        } catch (Exception e) {
            return "Ошибка при обработке XML: " + e.getMessage();
        }
    }
}
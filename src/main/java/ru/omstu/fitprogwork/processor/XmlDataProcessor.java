package ru.omstu.fitprogwork.processor;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

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

            Element currentElement = document.getDocumentElement();

            for (String part : parts) {
                if (part.isEmpty()) continue;

                if (part.matches("\\d+")) {
                    int index = Integer.parseInt(part);
                    NodeList children = currentElement.getChildNodes();
                    int elementIndex = 0;
                    Element foundElement = null;

                    for (int i = 0; i < children.getLength(); i++) {
                        Node child = children.item(i);
                        if (child.getNodeType() == Node.ELEMENT_NODE) {
                            if (elementIndex == index) {
                                foundElement = (Element) child;
                                break;
                            }
                            elementIndex++;
                        }
                    }

                    if (foundElement != null) {
                        currentElement = foundElement;
                    } else {
                        return "Элемент с индексом " + index + " не найден";
                    }
                } else {
                    // Ищем дочерний элемент по имени
                    NodeList nodeList = currentElement.getElementsByTagName(part);
                    if (nodeList.getLength() > 0) {
                        // Берем ПЕРВЫЙ элемент с таким именем
                        currentElement = (Element) nodeList.item(0);
                    } else {
                        return "Элемент не найден: " + part;
                    }
                }
            }

            return currentElement.getTextContent();

        } catch (Exception e) {
            return "Ошибка при обработке XML: " + e.getMessage();
        }
    }
}
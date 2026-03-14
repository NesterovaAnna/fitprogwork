package ru.omstu.fitprogwork.service;

import org.springframework.stereotype.Component;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class XmlDataExtractor implements DataExtractorService {

    @Override
    public String extract(String data, String path) throws Exception {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
            data.getBytes(StandardCharsets.UTF_8)
        );
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(inputStream);
        document.getDocumentElement().normalize();

        String[] parts = path.split("/");
        
        List<Element> currentElements = new ArrayList<>();
        currentElements.add(document.getDocumentElement());

        for (String part : parts) {
            if (part.isEmpty()) continue;

            List<Element> nextElements = new ArrayList<>();

            if (part.matches("\\d+")) {
                int index = Integer.parseInt(part);
                if (index < currentElements.size()) {
                    nextElements.add(currentElements.get(index));
                } else {
                    throw new Exception("Индекс " + index + " вне границ");
                }
            } else {
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
                throw new Exception("Элемент не найден: " + part);
            }
            currentElements = nextElements;
        }

        return currentElements.get(0).getTextContent();
    }

    @Override
    public String getType() {
        return "xml";
    }
}
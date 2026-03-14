package ru.omstu.fitprogwork.service;

import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;
import java.util.List;
import java.util.Map;

@Component
public class YamlDataExtractor implements DataExtractorService {

    private final Yaml yaml = new Yaml();

    @Override
    public String extract(String data, String path) throws Exception {
        Map<String, Object> root = yaml.load(data);
        String[] parts = path.split("/");
        
        Object current = root;
        for (String part : parts) {
            if (part.isEmpty()) continue;
            
            if (part.matches("\\d+")) {
                int index = Integer.parseInt(part);
                if (current instanceof List) {
                    List<?> list = (List<?>) current;
                    if (index < list.size()) {
                        current = list.get(index);
                    } else {
                        throw new Exception("Индекс " + index + " вне границ");
                    }
                } else {
                    throw new Exception("Ожидался массив, но получили: " + current.getClass());
                }
            } else {
                if (current instanceof Map) {
                    Map<?, ?> map = (Map<?, ?>) current;
                    current = map.get(part);
                } else {
                    throw new Exception("Ожидался объект, но получили: " + current.getClass());
                }
            }
            
            if (current == null) {
                throw new Exception("Путь не найден: " + part);
            }
        }
        
        return current.toString();
    }

    @Override
    public String getType() {
        return "yaml";
    }
}
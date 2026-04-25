package ru.omstu.fitprogwork.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@Service
public class CacheService {
    
    private static final Logger log = LoggerFactory.getLogger(CacheService.class);
    private final ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<>();

    public String get(String key) {
        String value = cache.get(key);
        if (value != null) {
            log.info("КЕШ: HIT - ключ '{}' найден в кеше", key);
        } else {
            log.info("КЕШ: MISS - ключ '{}' не найден", key);
        }
        return value;
    }

    public void put(String key, String value) {
        cache.put(key, value);
        log.info("КЕШ: сохранен ключ '{}' со значением '{}'", key, value);
    }

    public String getOrCompute(String key, Supplier<String> computer) {
        String cached = get(key);
        if (cached != null) {
            return cached;
        }
        log.info("КЕШ: вычисляем значение для ключа '{}'", key);
        String result = computer.get();
        put(key, result);
        return result;
    }

    public void clear() {
        cache.clear();
        log.info("КЕШ: очищен");
    }

    public void evictOlderThan(long milliseconds) {
        log.info("InMemory КЕШ: evictOlderThan не поддерживается (нет временных меток)");
    }
}
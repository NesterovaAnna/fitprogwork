package ru.omstu.fitprogwork.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.omstu.fitprogwork.db.CacheEntity;
import ru.omstu.fitprogwork.db.CacheRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Supplier;

@Service
public class DatabaseCacheService {
    
    private static final Logger log = LoggerFactory.getLogger(DatabaseCacheService.class);
    
    @Autowired
    private CacheRepository cacheRepository;

    public String get(String key) {
        Optional<CacheEntity> entity = cacheRepository.findById(key);
        if (entity.isPresent()) {
            log.info("DB КЕШ: HIT - ключ '{}' найден в БД", key);
            return entity.get().getValue();
        } else {
            log.info("DB КЕШ: MISS - ключ '{}' не найден в БД", key);
            return null;
        }
    }

    public void put(String key, String value) {
        CacheEntity entity = new CacheEntity(key, value);
        cacheRepository.save(entity);
        log.info("DB КЕШ: сохранен ключ '{}' в БД", key);
    }

    public String getOrCompute(String key, Supplier<String> computer) {
        String cached = get(key);
        if (cached != null) {
            return cached;
        }
        log.info("DB КЕШ: вычисляем значение для ключа '{}'", key);
        String result = computer.get();
        put(key, result);
        return result;
    }

    public void clear() {
        cacheRepository.deleteAll();
        log.info("DB КЕШ: очищена вся таблица кеша");
    }

    public void evictOlderThan(long milliseconds) {
        LocalDateTime threshold = LocalDateTime.now().minusNanos(milliseconds * 1_000_000);
        cacheRepository.deleteOlderThan(threshold);
        log.info("DB КЕШ: удалены записи, созданные до {}", threshold);
    }
}
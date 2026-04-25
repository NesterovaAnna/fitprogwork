package ru.omstu.fitprogwork.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.omstu.fitprogwork.cache.DatabaseCacheService;

@Component
@EnableScheduling
public class CacheCleanupScheduler {
    
    private static final Logger log = LoggerFactory.getLogger(CacheCleanupScheduler.class);
    
    @Autowired
    private DatabaseCacheService databaseCacheService;
    
    @Scheduled(fixedDelay = 30000)
    public void cleanOldEntries() {
        try {
            log.info("Планировщик: запущена очистка старых записей (старше 60 сек)");
            databaseCacheService.evictOlderThan(60000);
        } catch (Exception e) {
            log.error("Ошибка при очистке кеша: {}", e.getMessage());
        }
    }
    
    @Scheduled(cron = "0 0 0 * * SUN")
    public void clearCacheWeekly() {
        try {
            log.info("Планировщик: запущена еженедельная очистка всего кеша (воскресенье)");
            databaseCacheService.clear();
        } catch (Exception e) {
            log.error("Ошибка при еженедельной очистке кеша: {}", e.getMessage());
        }
    }
}
package ru.omstu.fitprogwork.service;

import org.junit.jupiter.api.Test;
import ru.omstu.fitprogwork.cache.CacheService;
import static org.junit.jupiter.api.Assertions.*;

class CacheServiceTest {

    private final CacheService cacheService = new CacheService();

    @Test
    void cacheHitAndMiss() {
        String key = "test-key";
        
        assertNull(cacheService.get(key));
        
        cacheService.put(key, "test-value");
        
        assertEquals("test-value", cacheService.get(key));
    }

    @Test
    void getOrComputeComputesOnce() {
        String key = "compute-key";
        int[] callCount = {0};
        
        String result = cacheService.getOrCompute(key, () -> {
            callCount[0]++;
            return "computed-value";
        });
        
        assertEquals("computed-value", result);
        assertEquals(1, callCount[0]);
        
        String result2 = cacheService.getOrCompute(key, () -> {
            callCount[0]++;
            return "new-value";
        });
        
        assertEquals("computed-value", result2);
        assertEquals(1, callCount[0]);
    }
}
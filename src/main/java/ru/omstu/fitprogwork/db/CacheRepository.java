package ru.omstu.fitprogwork.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

public interface CacheRepository extends JpaRepository<CacheEntity, String> {
    
    @Modifying
    @Transactional
    @Query("DELETE FROM CacheEntity c WHERE c.createdAt < :time")
    void deleteOlderThan(@Param("time") LocalDateTime time);
}
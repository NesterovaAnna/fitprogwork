package ru.omstu.fitprogwork.db;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cache_storage")
public class CacheEntity {
    
    @Id
    @Column(name = "cache_key")
    private String key;
    
    @Column(name = "cache_value")
    private String value;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    public CacheEntity() {}
    
    public CacheEntity(String key, String value) {
        this.key = key;
        this.value = value;
        this.createdAt = LocalDateTime.now();
    }
    
    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }
    
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
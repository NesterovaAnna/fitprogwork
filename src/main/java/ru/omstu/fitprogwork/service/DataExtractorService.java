package ru.omstu.fitprogwork.service;

public interface DataExtractorService {
    String extract(String data, String path) throws Exception;
    String getType();
}
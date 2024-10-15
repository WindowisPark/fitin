package com.fitin.communication.dto;

import java.time.LocalDateTime;

public class VideoMetadata {
    private String id;
    private String fileName;
    private long fileSize;
    private String mimeType;
    private int durationSeconds;
    private LocalDateTime createdAt;

    // Default constructor
    public VideoMetadata() {
        this.createdAt = LocalDateTime.now();
    }

    // Constructor with all fields
    public VideoMetadata(String id, String fileName, long fileSize, String mimeType, int durationSeconds) {
        this();
        this.id = id;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.mimeType = mimeType;
        this.durationSeconds = durationSeconds;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(int durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "VideoMetadata{" +
                "id='" + id + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileSize=" + fileSize +
                ", mimeType='" + mimeType + '\'' +
                ", durationSeconds=" + durationSeconds +
                ", createdAt=" + createdAt +
                '}';
    }
}
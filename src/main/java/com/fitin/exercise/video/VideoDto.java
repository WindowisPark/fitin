package com.fitin.exercise.video;

import java.time.LocalDateTime;

public class VideoDto {
    private String id;
    private String title;
    private String url;
    private LocalDateTime createdAt;

    // 기본 생성자
    public VideoDto() {}

    // 모든 필드를 포함한 생성자
    public VideoDto(String id, String title, String url, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.createdAt = createdAt;
    }

    // Getter 및 Setter 메서드
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
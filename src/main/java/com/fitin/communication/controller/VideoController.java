package com.fitin.communication.controller;

import com.fitin.communication.dto.VideoMetadata;
import com.fitin.communication.service.VideoTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/videos")
public class VideoController {

    @Autowired
    private VideoTransferService videoTransferService;

    /**
     * 특정 ID의 비디오를 조회합니다.
     * @param videoId 비디오 ID
     * @return 비디오 데이터와 함께 ResponseEntity 반환
     */
    @GetMapping("/{videoId}")
    public ResponseEntity<byte[]> getVideo(@PathVariable String videoId) {
        try {
            byte[] videoData = videoTransferService.getVideoData(videoId);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(videoData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * 모든 비디오의 메타데이터 목록을 조회합니다.
     * @return 비디오 메타데이터 목록
     */
    @GetMapping
    public ResponseEntity<List<VideoMetadata>> getAllVideos() {
        List<VideoMetadata> videos = videoTransferService.getAllVideoMetadata();
        return ResponseEntity.ok(videos);
    }

    /**
     * 특정 ID의 비디오 메타데이터를 조회합니다.
     * @param videoId 비디오 ID
     * @return 비디오 메타데이터
     */
    @GetMapping("/{videoId}/metadata")
    public ResponseEntity<VideoMetadata> getVideoMetadata(@PathVariable String videoId) {
        try {
            VideoMetadata metadata = videoTransferService.getVideoMetadata(videoId);
            return ResponseEntity.ok(metadata);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * 특정 ID의 비디오를 삭제합니다.
     * @param videoId 삭제할 비디오의 ID
     * @return 삭제 결과를 나타내는 ResponseEntity
     */
    @DeleteMapping("/{videoId}")
    public ResponseEntity<Void> deleteVideo(@PathVariable String videoId) {
        try {
            videoTransferService.deleteVideo(videoId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
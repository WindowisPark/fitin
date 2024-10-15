package com.fitin.communication.service;

import com.fitin.communication.dto.VideoMetadata;
import com.fitin.communication.util.VideoChunkUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class VideoTransferService {

    // 세션별 비디오 전송 상태를 추적하기 위한 맵
    private final Map<String, VideoTransferStatus> transferStatusMap = new HashMap<>();
    
    // 저장된 비디오의 메타데이터를 관리하기 위한 맵
    private final Map<String, VideoMetadata> videoMetadataMap = new HashMap<>();

    /**
     * 비디오 전송을 시작합니다.
     * @param session WebSocket 세션
     * @param metadata 비디오 메타데이터
     */
    public void startVideoTransfer(WebSocketSession session, VideoMetadata metadata) {
        String videoId = UUID.randomUUID().toString();
        VideoTransferStatus status = new VideoTransferStatus(videoId, metadata);
        transferStatusMap.put(session.getId(), status);
    }

    /**
     * 비디오 청크를 처리합니다.
     * @param session WebSocket 세션
     * @param chunkData 비디오 청크 데이터
     */
    public void processVideoChunk(WebSocketSession session, byte[] chunkData) {
        VideoTransferStatus status = transferStatusMap.get(session.getId());
        if (status != null) {
            status.addChunk(chunkData);
        } else {
            throw new IllegalStateException("Video transfer not started for this session");
        }
    }

    /**
     * 비디오 전송을 완료합니다.
     * @param session WebSocket 세션
     * @return 저장된 비디오의 ID
     */
    public String completeVideoTransfer(WebSocketSession session) {
        VideoTransferStatus status = transferStatusMap.remove(session.getId());
        if (status != null) {
            byte[] completeVideoData = VideoChunkUtil.mergeChunks(status.getChunks());
            String savedPath = saveVideo(completeVideoData, status.getMetadata());
            videoMetadataMap.put(status.getVideoId(), status.getMetadata());
            return status.getVideoId();
        } else {
            throw new IllegalStateException("No video transfer in progress for this session");
        }
    }

    /**
     * 비디오 데이터를 조회합니다.
     * @param videoId 비디오 ID
     * @return 비디오 데이터
     */
    public byte[] getVideoData(String videoId) {
        VideoMetadata metadata = videoMetadataMap.get(videoId);
        if (metadata == null) {
            throw new IllegalArgumentException("Video not found");
        }
        String filePath = "videos/" + metadata.getFileName();
        try {
            return VideoChunkUtil.readVideoFromFile(filePath);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read video", e);
        }
    }

    /**
     * 모든 비디오의 메타데이터를 조회합니다.
     * @return 비디오 메타데이터 목록
     */
    public List<VideoMetadata> getAllVideoMetadata() {
        return new ArrayList<>(videoMetadataMap.values());
    }

    /**
     * 특정 비디오의 메타데이터를 조회합니다.
     * @param videoId 비디오 ID
     * @return 비디오 메타데이터
     */
    public VideoMetadata getVideoMetadata(String videoId) {
        VideoMetadata metadata = videoMetadataMap.get(videoId);
        if (metadata == null) {
            throw new IllegalArgumentException("Video not found");
        }
        return metadata;
    }

    /**
     * 비디오를 삭제합니다.
     * @param videoId 삭제할 비디오의 ID
     */
    public void deleteVideo(String videoId) {
        VideoMetadata metadata = videoMetadataMap.remove(videoId);
        if (metadata == null) {
            throw new IllegalArgumentException("Video not found");
        }
        String filePath = "videos/" + metadata.getFileName();
        // 실제 파일 삭제 로직 구현 (예: Files.delete(Paths.get(filePath));)
        System.out.println("Deleted video file: " + filePath);
    }

    private String saveVideo(byte[] videoData, VideoMetadata metadata) {
        String filePath = "videos/" + metadata.getFileName();
        try {
            VideoChunkUtil.saveVideoToFile(videoData, filePath);
            return filePath;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save video", e);
        }
    }
    
    // 비디오 전송 상태를 추적하기 위한 내부 클래스
    
    private static class VideoTransferStatus {
        private final String videoId;
        private final VideoMetadata metadata;
        private final List<byte[]> chunks = new ArrayList<>();

        public VideoTransferStatus(String videoId, VideoMetadata metadata) {
            this.videoId = videoId;
            this.metadata = metadata;
        }

        public void addChunk(byte[] chunk) {
            chunks.add(chunk);
        }

        public List<byte[]> getChunks() {
            return chunks;
        }

        public String getVideoId() {
            return videoId;
        }

        public VideoMetadata getMetadata() {
            return metadata;
        }
    }
}
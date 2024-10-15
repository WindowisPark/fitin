package com.fitin.exercise.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;
    
    @Value("${video.storage.path:#{null}}") // TODO: 프로퍼티에 서버 storage 저장 위치 정해지면 바꿔야함! 
    private String videoStoragePath; 

    /**
     * 비디오를 저장하고 메타데이터를 데이터베이스에 기록합니다.
     * @param file 업로드된 비디오 파일
     * @param title 비디오 제목
     * @return 저장된 비디오의 DTO
     */
    public VideoDto saveVideo(MultipartFile file, String title) throws IOException {
    	
        // 프로퍼티 주석 처리 해놔서 추가해둔 임시 코드
    	if (videoStoragePath == null) {
            throw new IllegalStateException("Video storage path is not configured");
        }
        
        // 파일 이름 생성 (UUID 사용)
        String fileName = UUID.randomUUID().toString() + getFileExtension(file.getOriginalFilename());
        String filePath = videoStoragePath + fileName;
        
        // 파일 저장
        file.transferTo(new File(filePath));

        // 엔티티 생성 및 저장
        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setId(UUID.randomUUID().toString());
        videoEntity.setTitle(title);
        videoEntity.setUrl(filePath);
        videoEntity.setCreatedAt(LocalDateTime.now());

        VideoEntity savedVideo = videoRepository.save(videoEntity);
        
        // DTO로 변환하여 반환
        return convertToDto(savedVideo);
    }

    /**
     * ID로 비디오를 조회합니다.
     * @param id 비디오 ID
     * @return 조회된 비디오의 DTO
     */
    public VideoDto getVideo(String id) {
        VideoEntity videoEntity = videoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Video not found"));
        return convertToDto(videoEntity);
    }

    /**
     * 키워드로 비디오를 검색합니다.
     * @param keyword 검색 키워드
     * @return 검색된 비디오 DTO 리스트
     */
    public List<VideoDto> searchVideos(String keyword) {
        return videoRepository.findByTitleContaining(keyword).stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
    
    /**
     * 비디오를 삭제합니다.
     * @param id 삭제할 비디오의 ID
     * @throws RuntimeException 비디오가 존재하지 않거나 삭제 중 오류 발생 시
     */
    public void deleteVideo(String id) {
        VideoEntity video = videoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Video not found"));

        // 파일 시스템에서 비디오 파일 삭제
        try {
            Files.deleteIfExists(Paths.get(video.getUrl()));
        } catch (IOException e) {
            throw new RuntimeException("Error deleting video file", e);
        }

        // 데이터베이스에서 비디오 정보 삭제
        videoRepository.delete(video);
    }
    /**
     * VideoEntity를 VideoDto로 변환합니다.
     */
    private VideoDto convertToDto(VideoEntity entity) {
        return new VideoDto(
            entity.getId(),
            entity.getTitle(),
            entity.getUrl(),
            entity.getCreatedAt()
        );
    }

    /**
     * 파일 이름에서 확장자를 추출합니다.
     */
    private String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf("."));
    }
}
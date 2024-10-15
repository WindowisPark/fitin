package com.fitin.exercise.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController // 또는 @Controller, 사용 중인 어노테이션에 따라
@RequestMapping("/api/exercise/videos") // 필요에 따라 경로 조정
@Component("exerciseVideoController")
public class ExerciseVideoController {

    @Autowired
    private VideoService videoService;

    /**
     * 새 비디오를 업로드합니다.
     * @param file 업로드할 비디오 파일
     * @param title 비디오 제목
     * @return 업로드된 비디오의 정보
     */
    @PostMapping
    public ResponseEntity<VideoDto> uploadVideo(@RequestParam("file") MultipartFile file,
                                                @RequestParam("title") String title) {
        try {
            VideoDto videoDto = videoService.saveVideo(file, title);
            return ResponseEntity.ok(videoDto);
        } catch (IOException e) {
            // 로깅 추가 필요
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * ID로 특정 비디오를 조회합니다.
     * @param id 비디오 ID
     * @return 조회된 비디오 정보
     */
    @GetMapping("/{id}")
    public ResponseEntity<VideoDto> getVideo(@PathVariable String id) {
        try {
            VideoDto videoDto = videoService.getVideo(id);
            return ResponseEntity.ok(videoDto);
        } catch (RuntimeException e) {
            // 로깅 추가 필요
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 키워드로 비디오를 검색합니다.
     * @param keyword 검색 키워드
     * @return 검색된 비디오 목록
     */
    @GetMapping("/search")
    public ResponseEntity<List<VideoDto>> searchVideos(@RequestParam String keyword) {
        List<VideoDto> videos = videoService.searchVideos(keyword);
        return ResponseEntity.ok(videos);
    }

    /**
     * 지정된 ID의 비디오를 삭제합니다.
     * @param id 삭제할 비디오의 ID
     * @return 삭제 성공 여부에 따른 응답
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideo(@PathVariable String id) {
        try {
            videoService.deleteVideo(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            // 로깅 추가 필요
            return ResponseEntity.notFound().build();
        }
    }
}
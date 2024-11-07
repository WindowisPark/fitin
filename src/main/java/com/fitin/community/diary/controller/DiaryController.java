package com.fitin.community.diary.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitin.auth.entity.Member;
import com.fitin.community.diary.model.Diary;
import com.fitin.community.diary.service.DiaryService;

/**
 * 운동 일기 관련 API 엔드포인트를 제공하는 컨트롤러
 */
@RestController
@RequestMapping("/api/community/diaries")
public class DiaryController {

    private final DiaryService diaryService;

   
    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    /**
     * 새로운 운동 일기를 생성합니다.
     * @param diary 생성할 운동 일기 정보
     * @param member 인증된 사용자 정보
     * @return 생성된 운동 일기
     */
    @PostMapping
    public ResponseEntity<Diary> createDiary(@RequestBody Diary diary, @AuthenticationPrincipal Member member) {
        Diary createdDiary = diaryService.createDiary(diary, member);
        return ResponseEntity.ok(createdDiary);
    }

    /**
     * 현재 인증된 사용자의 모든 운동 일기를 조회합니다.
     * @param member 인증된 사용자 정보
     * @return 사용자의 운동 일기 목록
     */
    @GetMapping
    public ResponseEntity<List<Diary>> getUserDiaries(@AuthenticationPrincipal Member member) {
        List<Diary> diaries = diaryService.getUserDiaries(member);
        return ResponseEntity.ok(diaries);
    }

    /**
     * 특정 ID의 운동 일기를 조회합니다.
     * @param id 조회할 운동 일기 ID
     * @param member 인증된 사용자 정보
     * @return 조회된 운동 일기 또는 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Diary> getDiary(@PathVariable Long id, @AuthenticationPrincipal Member member) {
        return diaryService.getDiaryById(id)
                .filter(diary -> diary.getAuthor().equals(member))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * 운동 일기를 수정합니다.
     * @param id 수정할 운동 일기 ID
     * @param diary 수정할 운동 일기 정보
     * @param member 인증된 사용자 정보
     * @return 수정된 운동 일기 또는 404 Not Found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Diary> updateDiary(@PathVariable Long id, @RequestBody Diary diary, @AuthenticationPrincipal Member member) {
        return diaryService.getDiaryById(id)
                .filter(existingDiary -> existingDiary.getAuthor().equals(member))
                .map(existingDiary -> {
                    diary.setId(id);
                    diary.setAuthor(member);
                    return ResponseEntity.ok(diaryService.updateDiary(diary));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * 운동 일기를 삭제합니다.
     * @param id 삭제할 운동 일기 ID
     * @param member 인증된 사용자 정보
     * @return 성공 시 204 No Content, 실패 시 404 Not Found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiary(@PathVariable Long id, @AuthenticationPrincipal Member member) {
        return diaryService.getDiaryById(id)
                .filter(diary -> diary.getAuthor().equals(member))
                .map(diary -> {
                    diaryService.deleteDiary(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
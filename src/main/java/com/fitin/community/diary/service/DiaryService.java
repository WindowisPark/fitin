package com.fitin.community.diary.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitin.auth.entity.Member;
import com.fitin.community.common.exception.ResourceNotFoundException;
import com.fitin.community.diary.model.Diary;
import com.fitin.community.diary.repository.DiaryRepository;
import com.fitin.community.gamification.service.GamificationService;

@Service
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final GamificationService gamificationService;

    
    public DiaryService(DiaryRepository diaryRepository, GamificationService gamificationService) {
        this.diaryRepository = diaryRepository;
        this.gamificationService = gamificationService;
    }

    @Transactional
    public Diary createDiary(Diary diary, Member author) {
        diary.setAuthor(author);
        Diary savedDiary = diaryRepository.save(diary);
        
        gamificationService.rewardAction(author, "CREATE_DIARY");
        
        return savedDiary;
    }

    @Transactional(readOnly = true)
    public List<Diary> getUserDiaries(Member member) {
        return diaryRepository.findByAuthor(member);
    }

    @Transactional(readOnly = true)
    public Optional<Diary> getDiaryById(Long id) {
        return diaryRepository.findById(id);
    }

    @Transactional
    public Diary updateDiary(Diary diary) {
        return diaryRepository.save(diary);
    }

    @Transactional
    public void deleteDiary(Long id) {
        diaryRepository.deleteById(id);
    }

    @Transactional
    public Diary updateDiaryVisibility(Long id, boolean isPublic, Member member) {
        return diaryRepository.findById(id)
                .filter(diary -> diary.getAuthor().equals(member))
                .map(diary -> {
                    diary.setPublic(isPublic);
                    return diaryRepository.save(diary);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Diary not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Diary> getAllPublicDiaries() {
        return diaryRepository.findByIsPublicTrue();
    }
}
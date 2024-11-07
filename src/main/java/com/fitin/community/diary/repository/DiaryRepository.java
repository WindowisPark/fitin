package com.fitin.community.diary.repository;

import com.fitin.auth.entity.Member;
import com.fitin.community.diary.model.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Diary 엔티티에 대한 데이터 액세스를 제공하는 리포지토리 인터페이스
 */
@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {

    /**
     * 특정 회원의 모든 운동 일기를 조회합니다.
     * @param author 조회할 회원
     * @return 회원의 운동 일기 목록
     */
    List<Diary> findByAuthor(Member author);

    /**
     * 공개된 모든 운동 일기를 조회합니다.
     * @return 공개된 운동 일기 목록
     */
    List<Diary> findByIsPublicTrue();
}
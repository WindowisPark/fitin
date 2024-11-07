package com.fitin.community.common.repository;

import com.fitin.community.common.model.CommunityPost;
import com.fitin.auth.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityPostRepository extends JpaRepository<CommunityPost, Long> {
    List<CommunityPost> findByAuthor(Member author);
    List<CommunityPost> findByIsPublicTrue();
}
package com.fitin.community.common.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitin.auth.entity.Member;
import com.fitin.community.common.model.CommunityPost;
import com.fitin.community.common.repository.CommunityPostRepository;

/**
 * 커뮤니티 게시글 관련 비즈니스 로직을 처리하는 서비스 클래스
 */
@Service
public class CommunityService {

    private final CommunityPostRepository communityPostRepository;

    public CommunityService(CommunityPostRepository communityPostRepository) {
        this.communityPostRepository = communityPostRepository;
    }

    /**
     * 새로운 게시글을 생성합니다.
     * @param post 생성할 게시글 정보
     * @param author 게시글 작성자
     * @return 생성된 게시글
     */
    @Transactional
    public CommunityPost createPost(CommunityPost post, Member author) {
        post.setAuthor(author);
        return communityPostRepository.save(post);
    }

    /**
     * 게시글 ID로 게시글을 조회합니다.
     * @param postId 조회할 게시글 ID
     * @return 조회된 게시글 (Optional)
     */
    @Transactional(readOnly = true)
    public Optional<CommunityPost> getPostById(Long postId) {
        return communityPostRepository.findById(postId);
    }

    /**
     * 모든 공개 게시글을 조회합니다.
     * @return 공개 게시글 목록
     */
    @Transactional(readOnly = true)
    public List<CommunityPost> getAllPublicPosts() {
        return communityPostRepository.findByIsPublicTrue();
    }

    /**
     * 게시글을 업데이트합니다.
     * @param post 업데이트할 게시글 정보
     * @return 업데이트된 게시글
     */
    @Transactional
    public CommunityPost updatePost(CommunityPost post) {
        return communityPostRepository.save(post);
    }

    /**
     * 게시글을 삭제합니다.
     * @param postId 삭제할 게시글 ID
     */
    @Transactional
    public void deletePost(Long postId) {
        communityPostRepository.deleteById(postId);
    }

    /**
     * 사용자가 게시글을 볼 수 있는 권한이 있는지 확인합니다.
     * @param post 확인할 게시글
     * @param member 확인할 사용자
     * @return 볼 수 있으면 true, 없으면 false
     */
    @Transactional(readOnly = true)
    public boolean canViewPost(CommunityPost post, Member member) {
        return post.isPublic() || post.getAuthor().equals(member);
    }
}
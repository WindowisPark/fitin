package com.fitin.community.common.controller;

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
import com.fitin.community.common.model.CommunityPost;
import com.fitin.community.common.service.CommunityService;

/**
 * 커뮤니티 관련 API 엔드포인트를 제공하는 컨트롤러
 */
@RestController
@RequestMapping("/api/community")
public class CommunityController {

    private final CommunityService communityService;

    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    /**
     * 새로운 게시글을 생성합니다.
     * @param post 생성할 게시글 정보
     * @param member 인증된 사용자 정보
     * @return 생성된 게시글
     */
    @PostMapping("/posts")
    public ResponseEntity<CommunityPost> createPost(@RequestBody CommunityPost post, 
                                                    @AuthenticationPrincipal Member member) {
        CommunityPost createdPost = communityService.createPost(post, member);
        return ResponseEntity.ok(createdPost);
    }

    /**
     * 특정 게시글을 조회합니다.
     * @param postId 조회할 게시글 ID
     * @param member 인증된 사용자 정보
     * @return 조회된 게시글 또는 404 Not Found
     */
    @GetMapping("/posts/{postId}")
    public ResponseEntity<CommunityPost> getPost(@PathVariable Long postId, 
                                                 @AuthenticationPrincipal Member member) {
        return communityService.getPostById(postId)
                .filter(post -> communityService.canViewPost(post, member))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * 모든 공개 게시글을 조회합니다.
     * @return 공개 게시글 목록
     */
    @GetMapping("/posts")
    public ResponseEntity<List<CommunityPost>> getAllPublicPosts() {
        List<CommunityPost> posts = communityService.getAllPublicPosts();
        return ResponseEntity.ok(posts);
    }

    /**
     * 게시글을 수정합니다.
     * @param postId 수정할 게시글 ID
     * @param post 수정할 게시글 정보
     * @param member 인증된 사용자 정보
     * @return 수정된 게시글 또는 404 Not Found
     */
    @PutMapping("/posts/{postId}")
    public ResponseEntity<CommunityPost> updatePost(@PathVariable Long postId, 
                                                    @RequestBody CommunityPost post, 
                                                    @AuthenticationPrincipal Member member) {
        return communityService.getPostById(postId)
                .filter(existingPost -> existingPost.getAuthor().equals(member))
                .map(existingPost -> {
                    post.setId(postId);
                    post.setAuthor(member);
                    return ResponseEntity.ok(communityService.updatePost(post));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * 게시글을 삭제합니다.
     * @param postId 삭제할 게시글 ID
     * @param member 인증된 사용자 정보
     * @return 성공 시 204 No Content, 실패 시 404 Not Found
     */
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId, 
                                           @AuthenticationPrincipal Member member) {
        return communityService.getPostById(postId)
                .filter(post -> post.getAuthor().equals(member))
                .map(post -> {
                    communityService.deletePost(postId);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
package com.fitin.shopping.service;

import com.fitin.shopping.entity.Review;
import com.fitin.shopping.entity.ReviewImg;
import com.fitin.shopping.repository.ReviewImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewImgService {

    private final ReviewImgRepository reviewImgRepository;

    // 이미지 업로드 로직 (단순히 예시로 파일 시스템에 저장하는 경우)
    public void saveReviewImg(Review review, MultipartFile imgFile) throws Exception {
        // 예시: 파일을 서버에 저장하고 URL을 생성한 뒤 DB에 저장하는 방식
        String imgUrl = uploadFile(imgFile); // 파일 업로드 후 URL 반환 (구현 필요)
        ReviewImg reviewImg = new ReviewImg(imgUrl, review);
        reviewImgRepository.save(reviewImg);
    }

    // 리뷰에 연결된 이미지 조회
    public List<ReviewImg> getReviewImages(Long reviewId) {
        return reviewImgRepository.findByReviewId(reviewId);
    }

    // 이미지 파일 업로드 (예시로 로컬 파일 시스템에 저장)
    private String uploadFile(MultipartFile imgFile) throws Exception {
        // 파일 업로드 로직 구현
        // 예: 이미지 파일을 서버 특정 폴더에 저장하고, URL을 반환하는 로직
        String imgUrl = "/path/to/uploaded/file/" + imgFile.getOriginalFilename();
        // 실제 파일 업로드 처리는 생략
        return imgUrl;
    }

    // 이미지 삭제 로직
    public void deleteReviewImg(Long reviewImgId) {
        reviewImgRepository.deleteById(reviewImgId);
    }
}

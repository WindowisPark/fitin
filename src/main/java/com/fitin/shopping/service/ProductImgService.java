package com.fitin.shopping.service;

import com.fitin.shopping.dto.ProductImgDto;
import com.fitin.shopping.entity.Product;
import com.fitin.shopping.entity.ProductImg;
import com.fitin.shopping.exception.ProductImgNotFoundException;
import com.fitin.shopping.repository.ProductImgRepository;
import com.fitin.shopping.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductImgService {

    private final ProductImgRepository productImgRepository;
    private final ProductRepository productRepository;

    // 파일 저장 경로 지정 (로컬에 임시로 지정, 실제 환경에 맞게 수정)
    private final String filePath = "/uploads/";

    // 이미지 업로드
    @Transactional
    public ProductImgDto uploadProductImage(Long productId, MultipartFile file) throws IOException {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));

        // 파일을 서버에 저장
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File dest = new File(filePath + fileName);
        file.transferTo(dest);

        // 이미지 정보 저장
        ProductImg productImg = new ProductImg();
        productImg.setProduct(product);
        productImg.setImgUrl(filePath + fileName);
        productImgRepository.save(productImg);

        return new ProductImgDto(productImg);
    }

    // 이미지 조회
    @Transactional(readOnly = true)
    public List<ProductImgDto> getImagesByProductId(Long productId) {
        return productImgRepository.findByProductId(productId)
                .stream()
                .map(ProductImgDto::new)
                .collect(Collectors.toList());
    }

    // 이미지 삭제
    @Transactional
    public void deleteProductImage(Long imgId) {
        ProductImg productImg = productImgRepository.findById(imgId)
            .orElseThrow(() -> new ProductImgNotFoundException("Image not found"));

        // 서버에서 파일 삭제
        File file = new File(productImg.getImgUrl());
        if (file.exists()) {
            file.delete();
        }

        // 데이터베이스에서 이미지 정보 삭제
        productImgRepository.delete(productImg);
    }
    
    public ProductImg storeProductImage(Product product, MultipartFile imageFile) {
        // 이미지 파일 저장 로직 (파일 시스템, AWS S3 등)
        String imageUrl = uploadImageAndGetUrl(imageFile); // 예: 이미지 URL 반환

        // ProductImg 엔티티 생성
        ProductImg productImg = new ProductImg();
        productImg.setProduct(product);
        productImg.setImgUrl(imageUrl);

        return productImg;
    }

    private String uploadImageAndGetUrl(MultipartFile imageFile) {
        // 파일을 저장하고 URL을 반환하는 로직을 작성
        // 예: 파일 시스템에 저장 후 경로 반환
        return "http://example.com/image/" + imageFile.getOriginalFilename();
    }
    
    // ProductImg 엔티티를 데이터베이스에 저장하는 메서드.
    public void save(ProductImg productImg) {
        productImgRepository.save(productImg);
    }

    public void deleteImagesByProduct(Product product) {
        List<ProductImg> productImgs = productImgRepository.findByProduct(product);
        productImgRepository.deleteAll(productImgs);
    }

}

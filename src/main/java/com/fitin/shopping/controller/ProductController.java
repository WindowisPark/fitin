package com.fitin.shopping.controller;

import com.fitin.shopping.dto.ProductDto;
import com.fitin.shopping.dto.ProductCreateDto;
import com.fitin.shopping.dto.ProductUpdateDto;
import com.fitin.shopping.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    // 1. 상품 목록 조회
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // 2. 상품 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long id) {
        ProductDto product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }
    // 3. 상품 생성
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductCreateDto productCreateDto) {
        ProductDto newProduct = productService.createProduct(productCreateDto);
        return ResponseEntity.ok(newProduct);
    }

    // 4. 상품 수정
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
        @PathVariable("id") Long id, 
        @RequestBody ProductUpdateDto productUpdateDto
    ) {
        ProductDto updatedProduct = productService.updateProduct(id, productUpdateDto);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // 새로 추가된 메서드들

    // 6. 베스트셀러 상품 목록
    @GetMapping("/bestsellers")
    public ResponseEntity<List<ProductDto>> getBestsellers() {
        return ResponseEntity.ok(productService.getBestsellers());
    }

    // 7. 신상품 목록
    @GetMapping("/new-arrivals")
    public ResponseEntity<List<ProductDto>> getNewArrivals() {
        return ResponseEntity.ok(productService.getNewArrivals());
    }

    // 8. 추천 상품 목록
    @GetMapping("/recommended")
    public ResponseEntity<List<ProductDto>> getRecommendedProducts() {
        return ResponseEntity.ok(productService.getRecommendedProducts());
    }

    // 9. 최근 본 상품 목록
    @GetMapping("/recently-viewed")
    public ResponseEntity<List<ProductDto>> getRecentlyViewedProducts() {
        return ResponseEntity.ok(productService.getRecentlyViewedProducts());
    }
}
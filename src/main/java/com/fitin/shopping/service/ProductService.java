package com.fitin.shopping.service;

import com.fitin.shopping.dto.ProductCreateDto;
import com.fitin.shopping.dto.ProductDto;
import com.fitin.shopping.dto.ProductUpdateDto;
import com.fitin.shopping.entity.Product;
import com.fitin.shopping.exception.ProductNotFoundException;
import com.fitin.shopping.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    
    
    // 1. 상품 목록 조회
    @Transactional(readOnly = true)
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());
    }

    // 2. 상품 상세 조회
    @Transactional(readOnly = true)
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다. ID: " + id));
        return new ProductDto(product);
    }

    // 3. 상품 생성
    @Transactional
    public ProductDto createProduct(ProductCreateDto productCreateDto) {
        Product product = new Product(productCreateDto);
        product.setPrice(productCreateDto.getPrice() != null ? productCreateDto.getPrice() : BigDecimal.ZERO);
        Product savedProduct = productRepository.save(product);
        return convertToProductDto(savedProduct);  // convertToProductDto 메서드 사용
    }
    // 4. 상품 수정
    @Transactional
    public ProductDto updateProduct(Long id, ProductUpdateDto productUpdateDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다. ID: " + id));
        product.updateProduct(productUpdateDto);
        Product updatedProduct = productRepository.save(product);
        return convertToProductDto(updatedProduct);  // convertToProductDto 메서드 사용
    }

    // 5. 상품 삭제
    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("상품을 찾을 수 없습니다. ID: " + id);
        }
        productRepository.deleteById(id);
    }
    
    private ProductDto convertToProductDto(Product product) {
        return new ProductDto(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getStockQuantity(),
            product.getImageUrl()
        );
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getBestsellers() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Product> bestsellers = productRepository.findBestsellers(pageable);
        return bestsellers.stream()
                .map(this::convertToProductDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getNewArrivals() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Product> newArrivals = productRepository.findNewArrivals(pageable);
        return newArrivals.stream()
                .map(this::convertToProductDto)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<ProductDto> getRecommendedProducts() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Product> recommended = productRepository.findRecommendedProducts(pageable);
        return recommended.stream()
                .map(this::convertToProductDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getRecentlyViewedProducts() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Product> recentlyViewed = productRepository.findRecentlyViewedProducts(pageable);
        return recentlyViewed.stream()
                .map(this::convertToProductDto)
                .collect(Collectors.toList());
    }


}
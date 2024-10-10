package com.fitin.shopping.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSearchDto {

    // 검색 날짜 유형 (예: 전체, 1일, 1주, 1개월, 6개월)
    private String searchDateType;

    // 판매 상태 (판매 중, 품절 등)
    private String searchSellStatus;

    // 검색 유형 (상품명, 작성자 등)
    private String searchBy;

    // 검색어 (검색할 상품명이나 작성자 등)
    private String searchQuery = "";
}

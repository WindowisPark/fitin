package com.fitin.shopping.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class CategoryDto {
    private Long id;
    private String name;
    // 필요한 경우 추가 필드를 포함할 수 있습니다.
}
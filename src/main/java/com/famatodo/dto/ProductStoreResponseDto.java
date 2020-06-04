package com.famatodo.dto;

import java.util.List;

import lombok.Data;

@Data
public class ProductStoreResponseDto {

	private Long storeId;
	List<ProductStoreDto> products;
}

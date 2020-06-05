package com.famatodo.dto;

import lombok.Data;

@Data
public class ProductStoreDto {

	private Long productId;
	private String productName;
	private int quantity;
	private double productPrice;

}

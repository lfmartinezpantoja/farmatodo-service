package com.famatodo.dto;

import lombok.Data;

@Data
public class ProductDto {

	private int productIdentification;
	private String name;
	private double price;
	private String barCode;
}

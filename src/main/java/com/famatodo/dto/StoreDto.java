package com.famatodo.dto;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
public class StoreDto {

	@NotNull
	private String storeName;
	@NotNull
	private String schedule;
	@NotNull
	private String address;
}

package com.famatodo.dto;

import javax.persistence.Id;

import lombok.Data;

@Data
public class ShopDto {

	@Id
	private int shopIdentification;
	private String name;
	private String schedule;
	private String address;
}

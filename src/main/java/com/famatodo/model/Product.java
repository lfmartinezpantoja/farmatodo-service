package com.famatodo.model;

import java.io.Serializable;

import javax.persistence.Id;

import lombok.Data;

@Data
public class Product implements Serializable{

	@Id
	private int productId;
	private String name;
	private double price;
	private String barCode;
	private static final long serialVersionUID = 5943662237752965515L;
	
}

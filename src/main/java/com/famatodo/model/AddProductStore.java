package com.famatodo.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "products_stores")
public class AddProductStore implements Serializable{

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long product_store_id;
	private Long productId;
	private Long storeId;
	private int quantity;
	private static final long serialVersionUID = -6151499468293412079L;
	
	
}

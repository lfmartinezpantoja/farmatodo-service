package com.famatodo.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductStore implements Serializable {

	private Long productId;
	private String productName;
	private int quantity;
	private static final long serialVersionUID = -3530192598381957685L;

}

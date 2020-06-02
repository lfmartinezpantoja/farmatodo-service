package com.famatodo.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Shop implements Serializable{

	private int shopId;
	private String name;
	private String schedule;
	private String address;
	private static final long serialVersionUID = -8281676538443771419L;

	
}

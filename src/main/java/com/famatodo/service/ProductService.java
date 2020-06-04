package com.famatodo.service;

import com.famatodo.dto.ProductDto;
import com.famatodo.dto.ProductResponseDto;
import com.famatodo.exception.ServiceException;

public interface ProductService {

	public ProductResponseDto createProduct(ProductDto productDto) throws ServiceException;
	public ProductResponseDto updateProduct(ProductDto productDto) throws ServiceException;
	public ProductDto getProduct(String productName) throws ServiceException;
	public ProductResponseDto deleteProduct(String productName) throws ServiceException;
}

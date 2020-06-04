package com.famatodo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.famatodo.dto.ProductDto;
import com.famatodo.dto.ProductResponseDto;
import com.famatodo.exception.ServiceException;
import com.famatodo.service.ProductService;

@RestController
public class ProductController {

	@Autowired
	ProductService productService;
	
	@PostMapping("/products")
	public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductDto productDto) throws ServiceException{
		return new ResponseEntity<ProductResponseDto>(productService.createProduct(productDto), HttpStatus.OK);
	}
	@PatchMapping("/products")
	public ResponseEntity<ProductResponseDto> updateProduct(@RequestBody ProductDto productDto) throws ServiceException{
		return new ResponseEntity<ProductResponseDto>(productService.updateProduct(productDto), HttpStatus.OK);
	}
	@GetMapping("/products/{productName}")
	public ResponseEntity<ProductDto> getProduct(@PathVariable String productName) throws ServiceException{
		return new ResponseEntity<>(productService.getProduct(productName), HttpStatus.OK);
	}
	@DeleteMapping("/products/{productName}")
	public ResponseEntity<ProductResponseDto> deleteProduct(@PathVariable String productName) throws ServiceException{
		return new ResponseEntity<ProductResponseDto>(productService.deleteProduct(productName), HttpStatus.OK);
	}
	
}

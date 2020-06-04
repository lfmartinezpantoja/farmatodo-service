package com.famatodo.service.imp;

import static com.famatodo.error.ProductError.PRODUCT_DOESNT_EXIST;
import static com.famatodo.error.ProductError.PRODUCT_IS_ALREADY_REGISTERED;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.famatodo.dto.ProductDto;
import com.famatodo.dto.ProductResponseDto;
import com.famatodo.exception.ServiceException;
import com.famatodo.model.Product;
import com.famatodo.respository.ProductRepository;
import com.famatodo.service.ProductService;

import lombok.extern.java.Log;

@Log
@Service
public class ProductServiceImp implements ProductService {

	@Value("${product.post}")
	String saveProductMessage;

	@Value("${product.put}")
	String updateProductMessage;

	@Value("${product.delete}")
	String deleteProductMessage;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public ProductResponseDto createProduct(ProductDto productDto) throws ServiceException {
		Optional<Product> productCheck = productRepository.findByProductName(productDto.getProductName());
		if (productCheck.isPresent()) {
			throw new ServiceException(HttpStatus.BAD_REQUEST.value(), PRODUCT_IS_ALREADY_REGISTERED);
		}
		Product product = new Product();
		modelMapper.map(productDto, product);
		productRepository.save(product);
		log.info(String.format(saveProductMessage, product.getProductId()));
		return new ProductResponseDto(String.format(saveProductMessage, product.getProductId()),
				product.getProductId());
	}

	@Override
	public ProductResponseDto updateProduct(ProductDto productDto) throws ServiceException {
		Optional<Product> productCheck = productRepository.findByProductName(productDto.getProductName());
		if (!productCheck.isPresent()) {
			throw new ServiceException(HttpStatus.NOT_FOUND.value(), PRODUCT_DOESNT_EXIST);
		}
		modelMapper.map(productDto, productCheck.get());
		productRepository.save(productCheck.get());
		log.info(String.format(updateProductMessage, productCheck.get().getProductId()));
		return new ProductResponseDto(String.format(updateProductMessage, productCheck.get().getProductId()),
				productCheck.get().getProductId());

	}

	@Override
	public ProductDto getProduct(String productName) throws ServiceException {
		Optional<Product> productCheck = productRepository.findByProductName(productName);
		if (!productCheck.isPresent()) {
			throw new ServiceException(HttpStatus.NOT_FOUND.value(), PRODUCT_DOESNT_EXIST);
		}
		ProductDto productDto = new ProductDto();
		modelMapper.map(productCheck.get(), productDto);
		return productDto;
	}

	@Override
	public ProductResponseDto deleteProduct(String productName) throws ServiceException{
		Optional<Product> productCheck = productRepository.findByProductName(productName);
		if (!productCheck.isPresent()) {
			throw new ServiceException(HttpStatus.NOT_FOUND.value(), PRODUCT_DOESNT_EXIST);
		}
		
		productRepository.deleteById(productCheck.get().getProductId());
		
		log.info(String.format(deleteProductMessage, productCheck.get().getProductId()));
		return new ProductResponseDto(String.format(deleteProductMessage, productCheck.get().getProductId()),
				productCheck.get().getProductId());
	}

}

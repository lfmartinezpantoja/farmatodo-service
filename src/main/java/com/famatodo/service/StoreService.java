package com.famatodo.service;

import com.famatodo.dto.AddProductStoreDto;
import com.famatodo.dto.ProductStoreResponseDto;
import com.famatodo.dto.StoreDto;
import com.famatodo.dto.StoreResponseDto;
import com.famatodo.exception.ServiceException;

public interface StoreService {

	public StoreResponseDto createStore(StoreDto storeDto) throws ServiceException;

	public StoreResponseDto updateStore(StoreDto storeDto) throws ServiceException;

	public StoreDto getStore(String storeName) throws ServiceException;

	public StoreResponseDto deleteStore(String storeName) throws ServiceException;
	
	public StoreResponseDto addProductStore(AddProductStoreDto addProductDto) throws ServiceException;
	
	public StoreResponseDto editProductStore(AddProductStoreDto addProductDto) throws ServiceException;

	public ProductStoreResponseDto getProductStore(String storeName) throws ServiceException;
}

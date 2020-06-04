package com.famatodo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.famatodo.dto.AddProductStoreDto;
import com.famatodo.dto.ProductStoreResponseDto;
import com.famatodo.dto.StoreDto;
import com.famatodo.dto.StoreResponseDto;
import com.famatodo.exception.ServiceException;
import com.famatodo.service.StoreService;

@RestController
public class StoreController {

	@Autowired
	StoreService storeService;

	@PostMapping("/stores")
	public ResponseEntity<StoreResponseDto> createStore(@RequestBody @Validated StoreDto storeDto)
			throws ServiceException {
		return new ResponseEntity<StoreResponseDto>(storeService.createStore(storeDto), HttpStatus.OK);
	}

	@PatchMapping("/stores")
	public ResponseEntity<StoreResponseDto> updateStore(@RequestBody StoreDto storeDto) throws ServiceException {
		return new ResponseEntity<StoreResponseDto>(storeService.updateStore(storeDto), HttpStatus.OK);
	}

	@GetMapping("/stores/{storeName}")
	public ResponseEntity<StoreDto> getStore(@PathVariable String storeName) throws ServiceException {
		return new ResponseEntity<>(storeService.getStore(storeName), HttpStatus.OK);
	}

	@DeleteMapping("/stores/{storeName}")
	public ResponseEntity<StoreResponseDto> deleteStore(@PathVariable String storeName) throws ServiceException {
		return new ResponseEntity<StoreResponseDto>(storeService.deleteStore(storeName), HttpStatus.OK);
	}

	@PostMapping("/stores/addproduct")
	public ResponseEntity<StoreResponseDto> addProductToStore(@RequestBody AddProductStoreDto addProductDto)
			throws ServiceException {
		return new ResponseEntity<StoreResponseDto>(storeService.addProductStore(addProductDto), HttpStatus.OK);
		// return null;
	}

	@PutMapping("/stores/editproduct/changequantity")
	public ResponseEntity<StoreResponseDto> editProductToStore(@RequestBody AddProductStoreDto addProductDto)
			throws ServiceException {
		return new ResponseEntity<StoreResponseDto>(storeService.editProductStore(addProductDto), HttpStatus.OK);
		// return null;
	}

	@GetMapping("/stores/getproductbystore/{storeName}")
	public ResponseEntity<ProductStoreResponseDto> getProductByStore(@PathVariable String storeName)
			throws ServiceException {

		return new ResponseEntity<ProductStoreResponseDto>(storeService.getProductStore(storeName), HttpStatus.OK);

	}
}

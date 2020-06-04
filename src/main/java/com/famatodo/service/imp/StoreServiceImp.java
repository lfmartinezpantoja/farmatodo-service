package com.famatodo.service.imp;

import static com.famatodo.error.ProductError.PRODUCT_DOESNT_EXIST;
import static com.famatodo.error.StoreError.PRODUCT_DOESNT_EXIST_IN_STORE;
import static com.famatodo.error.StoreError.PRODUCT_IS_ALREADY_ADDED_IN_STORE;
import static com.famatodo.error.StoreError.STORE_DOESNT_EXIST;
import static com.famatodo.error.StoreError.STORE_IS_ALREADY_REGISTERED;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.famatodo.dto.AddProductStoreDto;
import com.famatodo.dto.ProductStoreDto;
import com.famatodo.dto.ProductStoreResponseDto;
import com.famatodo.dto.StoreDto;
import com.famatodo.dto.StoreResponseDto;
import com.famatodo.exception.ServiceException;
import com.famatodo.model.AddProductStore;
import com.famatodo.model.Product;
import com.famatodo.model.Store;
import com.famatodo.respository.AddProductStoreRepository;
import com.famatodo.respository.ProductRepository;
import com.famatodo.respository.StoreRepository;
import com.famatodo.service.StoreService;

import lombok.extern.java.Log;

@Log
@Service
public class StoreServiceImp implements StoreService {

	@Value("${store.post}")
	String saveStoreMessage;

	@Value("${store.put}")
	String updateStoreMessage;

	@Value("${store.delete}")
	String deleteStoreMessage;

	@Value("${store.addproduct}")
	String addProductStoreMessage;

	@Value("${store.editproduct}")
	String editProductStoreMessage;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	StoreRepository storeRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	AddProductStoreRepository productStoreRepository;

	@Override
	public StoreResponseDto createStore(StoreDto storeDto) throws ServiceException {
		Optional<Store> storeCheck = storeRepository.findByStoreName(storeDto.getStoreName());
		if (storeCheck.isPresent()) {
			throw new ServiceException(HttpStatus.BAD_REQUEST.value(), STORE_IS_ALREADY_REGISTERED);
		}
		Store store = new Store();
		modelMapper.map(storeDto, store);
		storeRepository.save(store);
		log.info(String.format(saveStoreMessage, store.getStoreId()));
		return new StoreResponseDto(String.format(saveStoreMessage, store.getStoreId()), store.getStoreId());
	}

	@Override
	public StoreResponseDto updateStore(StoreDto storeDto) throws ServiceException {
		Optional<Store> storeCheck = storeRepository.findByStoreName(storeDto.getStoreName());
		if (!storeCheck.isPresent()) {
			throw new ServiceException(HttpStatus.NOT_FOUND.value(), STORE_DOESNT_EXIST);
		}
		modelMapper.map(storeDto, storeCheck.get());
		storeRepository.save(storeCheck.get());
		log.info(String.format(saveStoreMessage, storeCheck.get().getStoreId()));
		return new StoreResponseDto(String.format(updateStoreMessage, storeCheck.get().getStoreId()),
				storeCheck.get().getStoreId());
	}

	@Override
	public StoreDto getStore(String storeName) throws ServiceException {
		Optional<Store> storeCheck = storeRepository.findByStoreName(storeName);
		if (!storeCheck.isPresent()) {
			throw new ServiceException(HttpStatus.NOT_FOUND.value(), STORE_DOESNT_EXIST);
		}
		StoreDto storeDto = new StoreDto();
		modelMapper.map(storeCheck.get(), storeDto);
		return storeDto;
	}

	@Override
	public StoreResponseDto deleteStore(String storeName) throws ServiceException {
		Optional<Store> storeCheck = storeRepository.findByStoreName(storeName);
		if (!storeCheck.isPresent()) {
			throw new ServiceException(HttpStatus.NOT_FOUND.value(), STORE_DOESNT_EXIST);
		}

		storeRepository.deleteById(storeCheck.get().getStoreId());

		log.info(String.format(deleteStoreMessage, storeCheck.get().getStoreId()));
		return new StoreResponseDto(String.format(deleteStoreMessage, storeCheck.get().getStoreId()),
				storeCheck.get().getStoreId());
	}

	@Override
	public StoreResponseDto addProductStore(AddProductStoreDto addProductDto) throws ServiceException {
		Optional<Product> productCheck = productRepository.findByProductName(addProductDto.getProductName());
		if (!productCheck.isPresent()) {
			throw new ServiceException(HttpStatus.NOT_FOUND.value(), PRODUCT_DOESNT_EXIST);
		}
		Optional<Store> storeCheck = storeRepository.findByStoreName(addProductDto.getStoreName());
		if (!storeCheck.isPresent()) {
			throw new ServiceException(HttpStatus.NOT_FOUND.value(), STORE_DOESNT_EXIST);
		}
		Optional<AddProductStore> checkProductInStore = productStoreRepository
				.findByProductIdAndStoreId(productCheck.get().getProductId(), storeCheck.get().getStoreId());
		if (checkProductInStore.isPresent()) {
			throw new ServiceException(HttpStatus.BAD_REQUEST.value(), PRODUCT_IS_ALREADY_ADDED_IN_STORE);
		}
		AddProductStore productStore = new AddProductStore();
		productStore.setProductId(productCheck.get().getProductId());
		productStore.setStoreId(storeCheck.get().getStoreId());
		productStore.setQuantity(addProductDto.getQuantity());
		productStoreRepository.save(productStore);
		log.info(String.format(addProductStoreMessage, addProductDto.getProductName(), addProductDto.getStoreName()));
		return new StoreResponseDto(
				String.format(addProductStoreMessage, addProductDto.getProductName(), addProductDto.getStoreName()),
				storeCheck.get().getStoreId());
	}

	@Override
	public StoreResponseDto editProductStore(AddProductStoreDto addProductDto) throws ServiceException {
		Optional<Product> productCheck = productRepository.findByProductName(addProductDto.getProductName());
		if (!productCheck.isPresent()) {
			throw new ServiceException(HttpStatus.NOT_FOUND.value(), PRODUCT_DOESNT_EXIST);
		}
		Optional<Store> storeCheck = storeRepository.findByStoreName(addProductDto.getStoreName());
		if (!storeCheck.isPresent()) {
			throw new ServiceException(HttpStatus.NOT_FOUND.value(), STORE_DOESNT_EXIST);
		}
		Optional<AddProductStore> checkProductInStore = productStoreRepository
				.findByProductIdAndStoreId(productCheck.get().getProductId(), storeCheck.get().getStoreId());
		if (!checkProductInStore.isPresent()) {
			throw new ServiceException(HttpStatus.BAD_REQUEST.value(), PRODUCT_DOESNT_EXIST_IN_STORE);
		}
		int beforeQuantity = checkProductInStore.get().getQuantity();
		checkProductInStore.get().setQuantity(addProductDto.getQuantity());
		productStoreRepository.save(checkProductInStore.get());
		log.info(String.format(editProductStoreMessage, addProductDto.getProductName(), beforeQuantity,
				checkProductInStore.get().getQuantity(), addProductDto.getStoreName()));
		return new StoreResponseDto(
				String.format(editProductStoreMessage, addProductDto.getProductName(), beforeQuantity,
						checkProductInStore.get().getQuantity(), addProductDto.getStoreName()),
				storeCheck.get().getStoreId());
	}

	@Override
	public ProductStoreResponseDto getProductStore(String storeName) throws ServiceException {
		Optional<Store> storeCheck = storeRepository.findByStoreName(storeName);
		if (!storeCheck.isPresent()) {
			throw new ServiceException(HttpStatus.NOT_FOUND.value(), STORE_DOESNT_EXIST);
		}
		List<Object[]> listProductsByStore = storeRepository.findProductsByStoreName(storeName);
		List<ProductStoreDto> products = new ArrayList<ProductStoreDto>();
		for (Object product[] : listProductsByStore) {
			ProductStoreDto productStoreDto = new ProductStoreDto();
			productStoreDto.setProductId(Long.parseLong(String.valueOf(product[0])));
			productStoreDto.setProductName(String.valueOf(product[1]));
			productStoreDto.setQuantity(Integer.parseInt((String.valueOf(product[2]))));
			products.add(productStoreDto);
		}
		ProductStoreResponseDto productStoreResponseDto = new ProductStoreResponseDto();
		productStoreResponseDto.setStoreId(storeCheck.get().getStoreId());
		productStoreResponseDto.setProducts(products);
		return productStoreResponseDto;
	}

}

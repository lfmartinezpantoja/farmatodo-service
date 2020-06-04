package com.famatodo.respository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.famatodo.model.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {

	public Optional<Store> findByStoreName(String storeName);

	@Query("SELECT ps.productId, p.productName, ps.quantity FROM AddProductStore ps JOIN Store s ON s.storeId = ps.storeId  JOIN Product p ON p.productId = ps.productId WHERE s.storeName = ?1")
	public List<Object[]> findProductsByStoreName(String storeName);
}

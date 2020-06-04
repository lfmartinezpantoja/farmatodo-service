package com.famatodo.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.famatodo.model.AddProductStore;


@Repository
public interface AddProductStoreRepository extends JpaRepository<AddProductStore, Long>{

	public Optional<AddProductStore> findByProductIdAndStoreId(Long productId, Long storeId);

}

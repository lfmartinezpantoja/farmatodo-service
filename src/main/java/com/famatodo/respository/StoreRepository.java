package com.famatodo.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.famatodo.model.Store;

public interface StoreRepository extends JpaRepository<Store, Long>{

	public Optional<Store> findByStoreName(String productName);
}

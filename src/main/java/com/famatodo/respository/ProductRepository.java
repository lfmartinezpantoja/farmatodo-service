package com.famatodo.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.famatodo.model.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

	public Optional<Product> findByProductName(String productName);

}

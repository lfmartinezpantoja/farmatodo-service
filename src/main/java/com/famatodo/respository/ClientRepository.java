package com.famatodo.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.famatodo.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

	public Optional<Client> findByIndentificationNumberOrEmail(int indentificationNumber, String email);

	public Optional<Client> findByIndentificationNumber(int identificationNumber);

	public Optional<Client> findByEmail(String email);
}

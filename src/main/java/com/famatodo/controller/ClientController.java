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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.famatodo.dto.ClientDto;
import com.famatodo.dto.ClientResponseDto;
import com.famatodo.exception.ServiceException;
import com.famatodo.service.ClientService;

@RestController
public class ClientController {

	@Autowired
	ClientService clientService;
	
	@PostMapping("/clients")
	public ResponseEntity<ClientResponseDto> createClient(@Validated @RequestBody  ClientDto clientDto) throws ServiceException{
		return new ResponseEntity<ClientResponseDto>(clientService.saveClient(clientDto), HttpStatus.OK);
	}
	
	@PatchMapping("/clients")
	public ResponseEntity<ClientResponseDto> updateClient(@RequestBody ClientDto clientDto) throws ServiceException{
		return new ResponseEntity<ClientResponseDto>(clientService.updateClient(clientDto), HttpStatus.OK);
	}
	
	@GetMapping("/clients/{identificationNumber}")
	public ResponseEntity<ClientDto> findClient(@PathVariable String identificationNumber) throws ServiceException{
		return new ResponseEntity<ClientDto>(clientService.getClient(identificationNumber),HttpStatus.OK);
	}
	@DeleteMapping("/clients/{identificationNumber}")
	public ResponseEntity<ClientResponseDto> disableClient(@PathVariable String identificationNumber) throws ServiceException{
		return new ResponseEntity<ClientResponseDto>(clientService.disableClient(identificationNumber),HttpStatus.OK);
	}
}

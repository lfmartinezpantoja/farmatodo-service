package com.famatodo.service;

import org.springframework.stereotype.Repository;

import com.famatodo.dto.ClientDto;
import com.famatodo.dto.ClientResponseDto;
import com.famatodo.exception.ServiceException;


@Repository
public interface ClientService {

	public ClientResponseDto saveClient(ClientDto clientDto) throws ServiceException;

	public ClientResponseDto updateClient(ClientDto clientDto) throws ServiceException;

	public ClientDto getClient(int identificationNumber) throws ServiceException;

	public ClientResponseDto disableClient(int identificationNumber) throws ServiceException;

}

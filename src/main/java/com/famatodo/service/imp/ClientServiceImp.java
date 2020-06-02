package com.famatodo.service.imp;

import static com.famatodo.error.Error.CLIENT_DOESNT_EXIST;
import static com.famatodo.error.Error.CLIENT_WITH_DOCUMENT_OR_EMAIL_ALREADY_EXIST;
import static com.famatodo.error.Error.EMAIL_IS_ALREADY_REGISTERED_FOR_OTHER_USER;
import static com.famatodo.error.Error.CLIENT_IS_DISABLED;
import java.util.Optional;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.famatodo.dto.ClientDto;
import com.famatodo.dto.ClientResponseDto;
import com.famatodo.exception.ServiceException;
import com.famatodo.model.Client;
import com.famatodo.respository.ClientRepository;
import com.famatodo.service.ClientService;

import lombok.extern.java.Log;

@Log
@Service
public class ClientServiceImp implements ClientService {

	@Value("${client.post}")
	String saveClientMessage;
	
	@Value("${client.put}")
	String updateClientMessage;
	
	@Value("${client.delete}")
	String deleteClientMessage;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	ClientRepository clientRepository;

	@Override
	public ClientResponseDto saveClient(ClientDto clientDto) throws ServiceException {
		Optional<Client> clientCheck = clientRepository
				.findByIndentificationNumberOrEmail(clientDto.getIndentificationNumber(), clientDto.getEmail());
		if (clientCheck.isPresent()) {
			throw new ServiceException(HttpStatus.BAD_REQUEST.value(), CLIENT_WITH_DOCUMENT_OR_EMAIL_ALREADY_EXIST);
		}
		Client client = new Client();
		modelMapper.map(clientDto, client);
		client.setEnabled(true);
		clientRepository.save(client);
		log.info(String.format(saveClientMessage, client.getIdentificationNumber()));
		return new ClientResponseDto(String.format(saveClientMessage, client.getIdentificationNumber()),client.getClientId());
	}

	@Override
	public ClientResponseDto updateClient(ClientDto clientDto) throws ServiceException {
		modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
		Optional<Client> clientCheck = clientRepository
				.findByIndentificationNumber(clientDto.getIndentificationNumber());
		if(!clientCheck.isPresent()) {
			 throw new ServiceException(HttpStatus.NOT_FOUND.value(), CLIENT_DOESNT_EXIST);
		}
		Optional<Client> clientByEmail = clientRepository.findByEmail(clientDto.getEmail());
		if(clientByEmail.isPresent() && clientByEmail.get().getIdentificationNumber()!=clientCheck.get().getIdentificationNumber()) {
			throw new ServiceException(HttpStatus.BAD_REQUEST.value(), EMAIL_IS_ALREADY_REGISTERED_FOR_OTHER_USER);
		}
		modelMapper.map(clientDto, clientCheck.get());
		clientRepository.save(clientCheck.get());
		log.info(String.format(updateClientMessage, clientCheck.get().getIdentificationNumber()));
		return new ClientResponseDto(String.format(updateClientMessage, clientCheck.get().getIdentificationNumber()), clientCheck.get().getClientId());
	}

	@Override
	public ClientDto getClient(int identificationNumber) throws ServiceException{
		ClientDto clientDto = new ClientDto();
		Optional<Client> clientCheck = clientRepository.findByIndentificationNumber(identificationNumber);
		if(!clientCheck.isPresent()) {
			throw new ServiceException(HttpStatus.NOT_FOUND.value(), CLIENT_DOESNT_EXIST);
		}
		if(!clientCheck.get().isEnabled()) {
			throw new ServiceException(HttpStatus.BAD_REQUEST.value(), CLIENT_IS_DISABLED);
		}
		modelMapper.map(clientCheck, clientDto);
		return clientDto;
	}

	@Override
	public ClientResponseDto disableClient(int identificationNumber) throws ServiceException{
		modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
		Optional<Client> clientCheck = clientRepository
				.findByIndentificationNumber(identificationNumber);
		if(!clientCheck.isPresent()) {
			 throw new ServiceException(HttpStatus.NOT_FOUND.value(), CLIENT_DOESNT_EXIST);
		}
		clientCheck.get().setEnabled(false);
		clientRepository.save(clientCheck.get());
		log.info(String.format(updateClientMessage, clientCheck.get().getIdentificationNumber()));
		return new ClientResponseDto(String.format(updateClientMessage, clientCheck.get().getIdentificationNumber()), clientCheck.get().getClientId());
	}

}

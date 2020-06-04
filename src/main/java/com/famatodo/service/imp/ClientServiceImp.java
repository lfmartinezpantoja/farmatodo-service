package com.famatodo.service.imp;

import static com.famatodo.error.ClientError.CLIENT_DOESNT_EXIST;
import static com.famatodo.error.ClientError.CLIENT_WITH_IDENTIFICATION_NUMBER_ALREADY_EXIST;
import static com.famatodo.error.ClientError.EMAIL_OR_USERNAME_ARE_ALREADY_REGISTERED_FOR_OTHER_CLIENT;
import static com.famatodo.error.ClientError.CLIENT_IS_DISABLED;
import static com.famatodo.error.ClientError.IDENTIFICATION_NUMBER_IS_REQUIRED;
import static com.famatodo.error.ClientError.USERNAME_DOESNT_EXIST;
import static com.famatodo.error.ClientError.PASSWORD_INCORRECT;
import java.util.List;
import java.util.Optional;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.famatodo.dto.ClientDto;
import com.famatodo.dto.ClientResponseDto;
import com.famatodo.dto.LoginDto;
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
	BCryptPasswordEncoder passwordEncoder;

	@Autowired
	ClientRepository clientRepository;

	@Override
	public ClientResponseDto saveClient(ClientDto clientDto) throws ServiceException {
		Optional<Client> clientCheck = clientRepository.findByIdentificationNumber(clientDto.getIdentificationNumber());
		if (clientCheck.isPresent()) {
			throw new ServiceException(HttpStatus.BAD_REQUEST.value(), CLIENT_WITH_IDENTIFICATION_NUMBER_ALREADY_EXIST);
		}

		List<Client> clientsRegistered = clientRepository.findByEmailOrUsername(clientDto.getEmail(),
				clientDto.getUsername());

		if (!clientsRegistered.isEmpty()) {
			for (Client clientByEmailOrUsername : clientsRegistered) {
				if (clientByEmailOrUsername.getUsername().equals(clientDto.getUsername())
						|| clientByEmailOrUsername.getEmail().equals(clientDto.getEmail())) {
					throw new ServiceException(HttpStatus.BAD_REQUEST.value(),
							EMAIL_OR_USERNAME_ARE_ALREADY_REGISTERED_FOR_OTHER_CLIENT);
				}
			}
		}
		Client client = new Client();
		modelMapper.map(clientDto, client);
		client.setPassword(passwordEncoder.encode(clientDto.getPassword()));
		client.setEnabled(true);
		clientRepository.save(client);
		log.info(String.format(saveClientMessage, client.getIdentificationNumber()));
		return new ClientResponseDto(String.format(saveClientMessage, client.getIdentificationNumber()),
				client.getClientId());
	}

	@Override
	public ClientResponseDto updateClient(ClientDto clientDto) throws ServiceException {
		modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
		if (clientDto.getIdentificationNumber() == null) {
			throw new ServiceException(HttpStatus.BAD_REQUEST.value(), IDENTIFICATION_NUMBER_IS_REQUIRED);
		}
		Optional<Client> clientCheck = clientRepository.findByIdentificationNumber(clientDto.getIdentificationNumber());
		if (!clientCheck.isPresent()) {
			throw new ServiceException(HttpStatus.NOT_FOUND.value(), CLIENT_DOESNT_EXIST);
		}
		if (clientDto.getUsername() == null) {
			clientDto.setUsername(clientCheck.get().getUsername());
		}
		if (clientDto.getEmail() == null) {
			clientDto.setEmail(clientCheck.get().getEmail());
		}
		List<Client> clientsRegistered = clientRepository.findByEmailOrUsername(clientDto.getEmail(),
				clientDto.getUsername());

		if (!clientsRegistered.isEmpty()) {
			for (Client clientByEmailOrUsername : clientsRegistered) {
				if (clientByEmailOrUsername.getIdentificationNumber() != clientDto.getIdentificationNumber()
						&& (clientByEmailOrUsername.getUsername().equals(clientDto.getUsername())
								|| clientByEmailOrUsername.getEmail().equals(clientDto.getEmail()))) {
					throw new ServiceException(HttpStatus.BAD_REQUEST.value(),
							EMAIL_OR_USERNAME_ARE_ALREADY_REGISTERED_FOR_OTHER_CLIENT);
				}
			}
		}
		modelMapper.map(clientDto, clientCheck.get());
		if (clientDto.getPassword() != null) {
			clientCheck.get().setPassword(passwordEncoder.encode(clientDto.getPassword()));
		}
		clientRepository.save(clientCheck.get());
		log.info(String.format(updateClientMessage, clientCheck.get().getIdentificationNumber()));
		return new ClientResponseDto(String.format(updateClientMessage, clientCheck.get().getIdentificationNumber()),
				clientCheck.get().getClientId());
	}

	@Override
	public ClientDto getClient(String identificationNumber) throws ServiceException {
		ClientDto clientDto = new ClientDto();
		Optional<Client> clientCheck = clientRepository.findByIdentificationNumber(identificationNumber);
		if (!clientCheck.isPresent()) {
			throw new ServiceException(HttpStatus.NOT_FOUND.value(), CLIENT_DOESNT_EXIST);
		}
		if (!clientCheck.get().isEnabled()) {
			throw new ServiceException(HttpStatus.BAD_REQUEST.value(), CLIENT_IS_DISABLED);
		}
		modelMapper.map(clientCheck.get(), clientDto);
		clientDto.setPassword(null);
		return clientDto;
	}

	@Override
	public ClientResponseDto disableClient(String identificationNumber) throws ServiceException {
		modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
		Optional<Client> clientCheck = clientRepository.findByIdentificationNumber(identificationNumber);
		if (!clientCheck.isPresent()) {
			throw new ServiceException(HttpStatus.NOT_FOUND.value(), CLIENT_DOESNT_EXIST);
		}
		clientCheck.get().setEnabled(false);
		clientRepository.save(clientCheck.get());
		log.info(String.format(updateClientMessage, clientCheck.get().getIdentificationNumber()));
		return new ClientResponseDto(String.format(updateClientMessage, clientCheck.get().getIdentificationNumber()),
				clientCheck.get().getClientId());
	}

	@Override
	public ClientDto login(LoginDto loginDto) throws ServiceException {
		Optional<Client> clientCheck = clientRepository.findByUsername(loginDto.getUsername());
		if(!clientCheck.isPresent()) {
			throw new ServiceException(HttpStatus.NOT_FOUND.value(), USERNAME_DOESNT_EXIST);
		}
		boolean passwordCheck = passwordEncoder.matches(loginDto.getPassword(),clientCheck.get().getPassword());
		if(!passwordCheck) {
			throw new ServiceException(HttpStatus.BAD_REQUEST.value(), PASSWORD_INCORRECT);
		}
		ClientDto clientDto = new ClientDto();
		modelMapper.map(clientCheck.get(), clientDto);
		clientDto.setPassword(null);
		return clientDto;
	}

}

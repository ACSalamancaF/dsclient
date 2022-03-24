package com.acsalamancaf.dsclient.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acsalamancaf.dsclient.dto.ClientDTO;
import com.acsalamancaf.dsclient.entities.Client;
import com.acsalamancaf.dsclient.repositories.ClientRepository;
import com.acsalamancaf.dsclient.service.exception.DataBaseException;
import com.acsalamancaf.dsclient.service.exception.ResourceNotFoundException;

@Service
public class ClientService {

	@Autowired
	public ClientRepository clientRepository;
	
	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPage(PageRequest pageRequest) {

		Page<Client> list = clientRepository.findAll(pageRequest);
		
		return list.map(c -> new ClientDTO(c));
	}

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> obj = clientRepository.findById(id);
		Client entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		
		return new ClientDTO(entity);
	}

	@Transactional
	public ClientDTO insert(ClientDTO dto) {
		
		Client entity = new Client();
		
		copyDtoToEntity(dto, entity);

		entity = clientRepository.save(entity);
		
		return new ClientDTO(entity);
	}
	
	@SuppressWarnings("deprecation")
	@Transactional
	public ClientDTO update(Long id, ClientDTO dto) {
		try {
			Client entity = clientRepository.getOne(id);
			copyDtoToEntity(dto, entity);
	
			entity = clientRepository.save(entity);
			
			return new ClientDTO(entity);
		}
		catch( EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found" + dto.getId());
		}
	}
	
	public void delete(Long id) {
		try {
			clientRepository.deleteById(id);
		}
		catch( EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found");
		}
		catch(DataIntegrityViolationException e){
			throw new DataBaseException("Data base exception");
		}
		
	}

	private void copyDtoToEntity(ClientDTO dto, Client entity) {
		entity.setName(dto.getName());
		entity.setCpf(dto.getCpf());
		entity.setBirthDate(dto.getBirthDate());
		entity.setChildren(dto.getChildren());		
	}

	

	
	
	

}

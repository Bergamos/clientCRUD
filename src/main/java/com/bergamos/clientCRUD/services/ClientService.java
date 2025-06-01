package com.bergamos.clientCRUD.services;

import com.bergamos.clientCRUD.dto.ClientDTO;
import com.bergamos.clientCRUD.entities.Client;
import com.bergamos.clientCRUD.repositories.ClientRepository;
import com.bergamos.clientCRUD.services.exceptions.DatabaseException;
import com.bergamos.clientCRUD.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ModelMapper updateModelMapper;

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id) {
        Client client = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
        return modelMapper.map(client, ClientDTO.class);

    }

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAll(Pageable pageable) {
        Page<Client> result = repository.findAll(pageable);
        return result.map(client -> modelMapper.map(client, ClientDTO.class));

    }

    @Transactional
    public ClientDTO insert(ClientDTO dto) {
        Client client = modelMapper.map(dto, Client.class);
        client = repository.save(client);
        return modelMapper.map(client, ClientDTO.class);
    }

    @Transactional
    public ClientDTO update(Long id, ClientDTO dto) {
        try {
            Client client = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
            updateModelMapper.map(dto, client);
            client = repository.save(client);
            return modelMapper.map(client, ClientDTO.class);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Resource not found");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Resource not found");
        }
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Referential integrity failure");
        }
    }


}

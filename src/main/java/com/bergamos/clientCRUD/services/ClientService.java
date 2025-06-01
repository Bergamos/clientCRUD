package com.bergamos.clientCRUD.services;

import com.bergamos.clientCRUD.dto.ClientDTO;
import com.bergamos.clientCRUD.entities.Client;
import com.bergamos.clientCRUD.repositories.ClientRepository;
import com.bergamos.clientCRUD.services.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    ModelMapper modelMapper = new ModelMapper();

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id) {
        Client client = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
        ClientDTO clientDTO = new ClientDTO();
        return  clientDTO = modelMapper.map(client, ClientDTO.class);

    }


}

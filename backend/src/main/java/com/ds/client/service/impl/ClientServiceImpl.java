package com.ds.client.service.impl;

import com.ds.client.dto.ClientDTO;
import com.ds.client.model.Client;
import com.ds.client.repository.ClientRepository;
import com.ds.client.service.ClientService;
import com.ds.client.service.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    @Override
    public Page<ClientDTO> findAll(final Pageable pageable) {
        return clientRepository.findAll(pageable)
                .map(client -> modelMapper.map(client, ClientDTO.class));
    }

    @Transactional(readOnly = true)
    @Override
    public ClientDTO findById(final Long id) {
        return clientRepository.findById(id)
                .map(client -> modelMapper.map(client, ClientDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
    }

    @Transactional
    @Override
    public ClientDTO insert(final ClientDTO clientDTO) {
        final Client client = clientRepository.save(modelMapper.map(clientDTO, Client.class));
        return modelMapper.map(client, ClientDTO.class);
    }

    @Transactional
    @Override
    public ClientDTO update(final Long id, final ClientDTO clientDTO) {
        try {
            final Client client = clientRepository.getOne(id);
            BeanUtils.copyProperties(clientDTO, client, "id");

            Client clientUpdate = clientRepository.save(client);

            return modelMapper.map(clientUpdate, ClientDTO.class);
        } catch (EntityNotFoundException ex) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    @Override
    public void delete(final Long id) {
        try {
            clientRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }
}

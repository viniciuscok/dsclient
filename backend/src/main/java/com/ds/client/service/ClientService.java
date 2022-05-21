package com.ds.client.service;

import com.ds.client.dto.ClientDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientService {

    Page<ClientDTO> findAll(Pageable pageable);

    ClientDTO findById(Long id);

    ClientDTO insert(ClientDTO categoryDTO);

    ClientDTO update(Long id, ClientDTO clientDto);

    void delete(Long id);
}

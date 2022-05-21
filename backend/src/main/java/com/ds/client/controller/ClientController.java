package com.ds.client.controller;

import com.ds.client.dto.ClientDTO;
import com.ds.client.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public ResponseEntity<Page<ClientDTO>> findAll(@PageableDefault(sort = {"name"}, size = 12, direction = Sort.Direction.ASC) final Pageable pageable) {
        return ResponseEntity.ok(clientService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> findById(@PathVariable final Long id) {
        return ResponseEntity.ok(clientService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ClientDTO> insert(@RequestBody final ClientDTO clientDTO) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(clientDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(clientService.insert(clientDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> update(@PathVariable final Long id, @RequestBody final ClientDTO clientDTO) {
        return ResponseEntity.ok().body(clientService.update(id, clientDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

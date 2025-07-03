package com.trindadeisencoes.crm.controller;

import com.trindadeisencoes.crm.model.Client;
import com.trindadeisencoes.crm.model.User;
import com.trindadeisencoes.crm.repository.ClientRepository;
import com.trindadeisencoes.crm.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;
import java.util.Optional;

@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/api/clientes")
public class ClientController {

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;

    public ClientController(ClientRepository clientRepository, UserRepository userRepository) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
    }

    // GET - Buscar todos os clientes (VENDEDOR e GESTOR)
    @GetMapping
    public List<Client> listarTodos() {
        return clientRepository.findAll();
    }

    // GET - Buscar clientes por nome (VENDEDOR e GESTOR)
    @GetMapping("/buscar")
    public List<Client> buscarPorNome(@RequestParam String nome) {
        return clientRepository.findByNameContainingIgnoreCase(nome);
    }

    // POST - Criar novo cliente (somente VENDEDOR)
    @PostMapping
    public ResponseEntity<Client> criar(@RequestBody @Valid Client client) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> vendedor = userRepository.findByEmail(email);

        if (vendedor.isPresent()) {
            client.setVendedor(vendedor.get());
            Client salvo = clientRepository.save(client);
            return ResponseEntity.ok(salvo);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    // PUT - Atualizar cliente existente (somente VENDEDOR)
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody @Valid Client novoCliente) {
        Optional<Client> existente = clientRepository.findById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Client cliente = existente.get();
        cliente.setName(novoCliente.getName());
        cliente.setCpf(novoCliente.getCpf());
        cliente.setEmail(novoCliente.getEmail());
        cliente.setTelefone(novoCliente.getTelefone());
        cliente.setWhatsapp(novoCliente.getWhatsapp());
        cliente.setEndereco(novoCliente.getEndereco());
        cliente.setCondutor(novoCliente.getCondutor());
        cliente.setStatus(novoCliente.getStatus());

        clientRepository.save(cliente);
        return ResponseEntity.ok(cliente);
    }

    // DELETE - Remover cliente (somente GESTOR)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        if (!clientRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        clientRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
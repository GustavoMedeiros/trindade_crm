package com.trindadeisencoes.crm.service;

import com.trindadeisencoes.crm.model.Client;
import com.trindadeisencoes.crm.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> listarTodos() {
        return clientRepository.findAll();
    }

    public Optional<Client> buscarPorId(Long id) {
        return clientRepository.findById(id);
    }

    public List<Client> buscarPorNome(String nome) {
        return clientRepository.findByNameContainingIgnoreCase(nome);
    }

    public Client cadastrar(Client client) {
        return clientRepository.save(client);
    }

    public Client atualizar(Long id, Client clientAtualizado) {
        return clientRepository.findById(id).map(client -> {
            client.setName(clientAtualizado.getName());
            client.setCpf(clientAtualizado.getCpf());
            client.setRg(clientAtualizado.getRg());
            client.setNascimento(clientAtualizado.getNascimento());
            client.setNacionalidade(clientAtualizado.getNacionalidade());
            client.setEstadoCivil(clientAtualizado.getEstadoCivil());
            client.setProfissao(clientAtualizado.getProfissao());
            client.setTelefone(clientAtualizado.getTelefone());
            client.setWhatsapp(clientAtualizado.getWhatsapp());
            client.setEmail(clientAtualizado.getEmail());
            client.setEndereco(clientAtualizado.getEndereco());
            client.setCondutor(clientAtualizado.getCondutor());
            client.setStatus(clientAtualizado.getStatus());
            client.setVendedor(clientAtualizado.getVendedor());
            return clientRepository.save(client);
        }).orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + id));
    }

    public void deletar(Long id) {
        clientRepository.deleteById(id);
    }
}
package com.ssu.maxshkodin.springrest.services.impl;

import com.ssu.maxshkodin.springrest.models.Client;
import com.ssu.maxshkodin.springrest.repository.ClientRepository;
import com.ssu.maxshkodin.springrest.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Client add(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public void delete(int id) {
        clientRepository.deleteById(id);
    }

    @Override
    public Client update(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client getById(int id) {
        return clientRepository.findById(id).get();
    }

    @Override
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client getClientByUsername(String username) {
        return clientRepository.findAll().stream().filter(c->c.getLogin().equals(username)).findFirst().orElse(null);
    }
}

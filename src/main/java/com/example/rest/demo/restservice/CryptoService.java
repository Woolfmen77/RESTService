package com.example.rest.demo.restservice;

import com.example.rest.demo.entitymodel.Cryptocurrency;
import com.example.rest.demo.entitymodel.User;

import java.util.List;

public interface CryptoService {

    List<Cryptocurrency> getAll();

    Cryptocurrency getById(Long id);

    Cryptocurrency findBySymbol(String symbol);

    void addNewUser(User user);

    void saveCrypto(com.example.rest.demo.restmodel.Cryptocurrency[] url);
}
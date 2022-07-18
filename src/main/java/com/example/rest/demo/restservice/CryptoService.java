package com.example.rest.demo.restservice;

import com.example.rest.demo.entitymodel.Cryptocurrency;

import java.util.List;

public interface CryptoService {

    List<Cryptocurrency> getAll();

    void saveCrypto(com.example.rest.demo.dto.Cryptocurrency[] url);

    Cryptocurrency findBySymbol(String symbol);
}
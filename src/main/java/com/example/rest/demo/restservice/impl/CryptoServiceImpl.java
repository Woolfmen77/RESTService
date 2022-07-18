package com.example.rest.demo.restservice.impl;

import com.example.rest.demo.entitymodel.Cryptocurrency;
import com.example.rest.demo.repository.CryptoRepository;
import com.example.rest.demo.restservice.CryptoService;
import com.example.rest.demo.restservice.UserService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Getter
public class CryptoServiceImpl implements CryptoService {

    @Autowired
    CryptoRepository cryptoRepository;
    @Autowired
    UserService userService;

    public CryptoServiceImpl() {
    }

    @Override
    public List<Cryptocurrency> getAll() {
        return cryptoRepository.findAll();
    }

    @Override
    public void saveCrypto(com.example.rest.demo.dto.Cryptocurrency[] url) {
        Cryptocurrency crypto = new Cryptocurrency();
        for (com.example.rest.demo.dto.Cryptocurrency item : url) {
            crypto.setId(item.getId());
            crypto.setSymbol(item.getSymbol());
            crypto.setPriceUsd(item.getPrice_usd());
        }
        cryptoRepository.save(crypto);
    }

    @Override
    public Cryptocurrency findBySymbol(String symbol) {
        return cryptoRepository.findBySymbol(symbol);
    }
}
package com.example.rest.demo.restservice;

import com.example.rest.demo.dto.Cryptocurrency;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

@Component
public class MyScheduler {

    @Autowired
    private CryptoService cryptoService;

    final String sol = "https://api.coinlore.net/api/ticker/?id=48543";
    final String eth = "https://api.coinlore.net/api/ticker/?id=80";
    final String btc = "https://api.coinlore.net/api/ticker/?id=90";

    public MyScheduler() {
    }

    public void scheduled(String urlCrypto) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        try {
            Cryptocurrency[] url = objectMapper.readValue(new URL(urlCrypto), Cryptocurrency[].class);
            cryptoService.saveCrypto(url);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    void doWork() {
        scheduled(sol);
        scheduled(eth);
        scheduled(btc);
    }
}
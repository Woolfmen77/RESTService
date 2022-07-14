package com.example.rest.demo.restservice;

import com.example.rest.demo.restmodel.Cryptocurrency;
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

    public MyScheduler() throws IOException {
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    void doWork() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);

        try {
            Cryptocurrency[] url_sol = objectMapper.readValue(new URL("https://api.coinlore.net/api/ticker/?id=48543"), Cryptocurrency[].class);
            cryptoService.saveCrypto(url_sol);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try {
            Cryptocurrency[] url_eth = objectMapper.readValue(new URL("https://api.coinlore.net/api/ticker/?id=80"), Cryptocurrency[].class);
            cryptoService.saveCrypto(url_eth);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try {
            Cryptocurrency[] url_bts = objectMapper.readValue(new URL("https://api.coinlore.net/api/ticker/?id=90"), Cryptocurrency[].class);
            cryptoService.saveCrypto(url_bts);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
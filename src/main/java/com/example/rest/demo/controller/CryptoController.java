package com.example.rest.demo.controller;

import com.example.rest.demo.entitymodel.Cryptocurrency;
import com.example.rest.demo.entitymodel.User;
import com.example.rest.demo.restservice.CryptoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping
public class CryptoController {

    private final Object sync = new Object();
    private final List<User> userList = new ArrayList<>();
    private final CryptoService cryptoService;

    @Autowired
    public CryptoController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @GetMapping(value = "/api/cryptos")
    public ResponseEntity<String> all() {
        final List<Cryptocurrency> cryptoList = cryptoService.getAll();
        return !cryptoList.isEmpty()
                ? new ResponseEntity<>(cryptoList.toString(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/api/cryptos/{id}")
    public ResponseEntity<?> cryptoPrice(@PathVariable(name = "id") Long id) {
        final Cryptocurrency crypto = cryptoService.getById(id);
        return crypto != null
                ? new ResponseEntity<>(crypto.getSymbol() + " : " + crypto.getPrice_usd(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/api/cryptos")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        cryptoService.addNewUser(user);
        userList.add(user);

        new Thread(() -> {
            synchronized (sync) {
                sync.notify();
            }
        }).start();

        new Thread(() -> {
            synchronized (sync) {
                while (userList.isEmpty()) {
                    try {
                        sync.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        User userNew = userList.get(0);
        userList.remove(0);
        while (true) {
            Cryptocurrency cryptocurrency = cryptoService.findBySymbol(userNew.getSymbol());
            BigDecimal cryptoPrice = cryptocurrency.getPrice_usd();
            BigDecimal userPrise = userNew.getUserCrypto().getPrice_usd();
            double cryptoD = cryptoPrice.doubleValue();
            double userD = userPrise.doubleValue();

            if (cryptoD > userD) {
                double maxPercent = (cryptoD - userD) / cryptoD * 100;
                int percentMax = (int) maxPercent;
                log.warn(userNew.getSymbol() + " " + userNew.getUsername() + " + " + Math.abs(percentMax) + "%");
                return new ResponseEntity<>(HttpStatus.OK);
            }
            if (cryptoD < userD) {
                double minPercent = (cryptoD - userD) / cryptoD * 100;
                int percentMin = (int) minPercent;
                log.warn(userNew.getSymbol() + " " + userNew.getUsername() + " - " + Math.abs(percentMin) + "%");
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
    }
}
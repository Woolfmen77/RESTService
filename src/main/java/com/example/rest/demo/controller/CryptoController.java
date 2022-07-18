package com.example.rest.demo.controller;

import com.example.rest.demo.entitymodel.Cryptocurrency;
import com.example.rest.demo.entitymodel.User;
import com.example.rest.demo.restservice.CryptoService;
import com.example.rest.demo.restservice.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Slf4j
@RestController
@RequestMapping
public class CryptoController {

    private final User userNotified = new User();
    private final CryptoService cryptoService;
    private final UserService userService;

    @Autowired
    public CryptoController(CryptoService cryptoService, UserService userService) {
        this.cryptoService = cryptoService;
        this.userService = userService;
    }

    @GetMapping(value = "/api/cryptos")
    public ResponseEntity<String> all() {
        final List<Cryptocurrency> cryptoList = cryptoService.getAll();
        return !cryptoList.isEmpty()
                ? new ResponseEntity<>(cryptoList.toString(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/api/cryptos/{symbol}")
    public ResponseEntity<?> cryptoPrice(@PathVariable(name = "symbol") String symbol) {
        Cryptocurrency crypto = cryptoService.findBySymbol(symbol);
        return crypto.getPriceUsd() != null
                ? new ResponseEntity<>(crypto.getSymbol() + " : " + crypto.getPriceUsd(), HttpStatus.OK)
                : new ResponseEntity<>("Please try again in a minute", HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/api/cryptos")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        Cryptocurrency crypto = cryptoService.findBySymbol(user.getSymbol());
        if (crypto == null) {
            return new ResponseEntity<>("Incorrect data", HttpStatus.OK);
        }
        if (userService.findByUsername(user.getUsername()) != null) {
            return new ResponseEntity<>("User with this name is already registered!", HttpStatus.BAD_REQUEST);
        } else {
            if (crypto.getPriceUsd() == null) {
                return new ResponseEntity<>("Please try again in a minute", HttpStatus.OK);
            } else {
                userService.addNewUser(user);
                new Thread(() -> {
                    synchronized (userNotified) {
                        userNotified.notify();
                    }
                }).start();
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
    }
}
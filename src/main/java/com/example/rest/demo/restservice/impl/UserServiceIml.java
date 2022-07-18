package com.example.rest.demo.restservice.impl;

import com.example.rest.demo.entitymodel.Cryptocurrency;
import com.example.rest.demo.entitymodel.User;
import com.example.rest.demo.entitymodel.UserCrypto;
import com.example.rest.demo.repository.CryptoRepository;
import com.example.rest.demo.repository.UserRepository;
import com.example.rest.demo.restservice.MyQueue;
import com.example.rest.demo.restservice.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanCreationNotAllowedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class UserServiceIml implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CryptoRepository cryptoRepository;

    public UserServiceIml() {

    }

    @Override
    public Cryptocurrency findBySymbol(String symbol) {
        return cryptoRepository.findBySymbol(symbol);
    }

    @Override
    public void addNewUser(User user) {
        UserCrypto userCrypto = new UserCrypto();
        Cryptocurrency cryptocurrency = findBySymbol(user.getSymbol());
        userCrypto.setPriceUsd(cryptocurrency.getPriceUsd());
        user.setUserCrypto(userCrypto);
        userRepository.save(user);
        User newUser = userRepository.findByUsername(user.getUsername());
        MyQueue myQueue = new MyQueue();
        myQueue.put(newUser);
        Thread thread = new Thread(new User() {
            public void run() {
                User user = myQueue.get();
                work(user);
            }
        });
        thread.start();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void work(User userNew) {
        while (true) {
            try {
                Cryptocurrency cryptocurrency = findBySymbol(userNew.getSymbol());
                BigDecimal cryptoPrice = cryptocurrency.getPriceUsd();
                BigDecimal userPrise = userNew.getUserCrypto().getPriceUsd();
                double cryptoD = cryptoPrice.doubleValue();
                double userD = userPrise.doubleValue();
                if (cryptoD > userD) {
                    double maxPercent = (cryptoD - userD) / cryptoD * 100;
                    int percentMax = (int) maxPercent;
                    if (percentMax > 1) {
                        log.warn(userNew.getSymbol() + ", " + userNew.getUsername() + ", +" + Math.abs(percentMax) + "%");
                        break;
                    }
                }
                if (cryptoD < userD) {
                    double minPercent = (cryptoD - userD) / cryptoD * 100;
                    int percentMin = (int) minPercent;
                    if (percentMin < 1) {
                        log.warn(userNew.getSymbol() + ", " + userNew.getUsername() + ", -" + Math.abs(percentMin) + "%");
                        break;
                    }
                }
            } catch (BeanCreationNotAllowedException ignored) {
                return;
            }
        }
    }
}
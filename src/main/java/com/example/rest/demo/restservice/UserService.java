package com.example.rest.demo.restservice;

import com.example.rest.demo.entitymodel.Cryptocurrency;
import com.example.rest.demo.entitymodel.User;

public interface UserService {

    void work(User userNew);

    void addNewUser(User user);

    User findByUsername(String username);

    Cryptocurrency findBySymbol(String symbol);
}

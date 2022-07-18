package com.example.rest.demo.restservice;

import com.example.rest.demo.entitymodel.User;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@Getter
@Setter
public class MyQueue {

    private final List<User> userList = new ArrayList<>();

    public synchronized void put(User userNew) {
        userList.add(userNew);
        notify();
    }

    public synchronized User get() {
        while (userList.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        User user = userList.get(0);
        userList.remove(0);
        return user;
    }
}
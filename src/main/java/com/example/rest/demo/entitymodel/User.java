package com.example.rest.demo.entitymodel;

import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class User implements Runnable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String username;
    @Column
    private String symbol;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_crypto_id", referencedColumnName = "id")
    private UserCrypto userCrypto;

    public void run() {
    }
}
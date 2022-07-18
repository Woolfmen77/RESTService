package com.example.rest.demo.entitymodel;

import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_crypto")
@Getter
@Setter
public class UserCrypto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column(name = "price_usd")
    private BigDecimal priceUsd;
}
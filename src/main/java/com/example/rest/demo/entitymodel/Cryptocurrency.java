package com.example.rest.demo.entitymodel;

import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "crypto")
@Getter
@Setter
@NoArgsConstructor
public class Cryptocurrency {
    @Id
    @Column
    private Long id;
    @Column
    private String symbol;
    @Column(name = "price_usd")
    private BigDecimal priceUsd;

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", symbol='" + symbol + '\'' +
                '}';
    }

    public Cryptocurrency(int id, String symbol) {
        this.id = (long) id;
        this.symbol = symbol;
    }
}
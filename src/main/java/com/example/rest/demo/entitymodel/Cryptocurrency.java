package com.example.rest.demo.entitymodel;

import javax.persistence.*;
import java.math.BigDecimal;

import lombok.*;

@Entity
@Table(name = "crypto")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cryptocurrency {

    @Id
    @Column
    private Long id;
    @Column
    private String symbol;
    @Column
    private String name;
    @Column
    private String nameid;
    @Column(name = "`rank`", nullable = true)
    private String rank;
    @Column
    private BigDecimal price_usd;
    @Column
    private String percent_change_24h;
    @Column
    private String percent_change_1h;
    @Column
    private String percent_change_7d;
    @Column
    private String market_cap_usd;
    @Column
    private String volume24;
    @Column
    private String volume24_native;
    @Column
    private String csupply;
    @Column
    private String price_btc;
    @Column
    private String tsupply;
    @Column
    private String msupply;

    @Override
    public String toString() {
        return "{" +
                "'id'=" + "'" + id + "'" +
                ", 'symbol'=" + "'" + symbol + "'" +
                '}';
    }

    public Cryptocurrency(int id, String symbol, double price_usd) {
        this.id = (long) id;
        this.symbol = symbol;
        this.price_usd = new BigDecimal(price_usd);
    }
}
package com.example.rest.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Setter
@Getter
@ToString
public class Cryptocurrency {
    private Long id;
    private String symbol;
    private String name;
    private String nameid;
    private String rank;
    private BigDecimal price_usd;
    private String percent_change_24h;
    private String percent_change_1h;
    private String percent_change_7d;
    private String market_cap_usd;
    private String volume24;
    private String volume24_native;
    private String csupply;
    private String price_btc;
    private String tsupply;
    private String msupply;
}
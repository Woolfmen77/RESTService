package com.example.rest.demo.entitymodel;

import javax.persistence.*;
import java.math.BigDecimal;

import lombok.*;

@Entity
@Table(name = "user_crypto")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCrypto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private BigDecimal price_usd;
    @OneToOne(mappedBy = "userCrypto")
    private User user;
}
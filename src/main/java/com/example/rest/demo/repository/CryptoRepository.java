package com.example.rest.demo.repository;

import com.example.rest.demo.entitymodel.Cryptocurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoRepository extends JpaRepository<Cryptocurrency, Long> {
    Cryptocurrency findBySymbol(String symbol);
}
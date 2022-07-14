package com.example.rest.demo.configuration;

import com.example.rest.demo.entitymodel.Cryptocurrency;
import com.example.rest.demo.repository.CryptoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DbConfig {

    @Bean
    public CommandLineRunner commandLineRunner(CryptoRepository cryptoRepository) {
        return args -> {
            cryptoRepository.saveAll(List.of(
                    new Cryptocurrency(90, "BTC", 0.00),
                    new Cryptocurrency(80, "ETH", 0.00),
                    new Cryptocurrency(48543, "SOL", 0.00)
            ));
        };
    }
}
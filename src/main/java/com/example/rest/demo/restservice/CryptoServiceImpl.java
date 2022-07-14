package com.example.rest.demo.restservice;

import com.example.rest.demo.entitymodel.Cryptocurrency;
import com.example.rest.demo.entitymodel.User;
import com.example.rest.demo.entitymodel.UserCrypto;
import com.example.rest.demo.repository.CryptoRepository;
import com.example.rest.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@Service
public class CryptoServiceImpl implements CryptoService {

    private final CryptoRepository cryptoRepository;
    private final UserRepository userRepository;

    @Autowired
    public CryptoServiceImpl(CryptoRepository cryptoRepository, UserRepository userRepository) {
        this.cryptoRepository = cryptoRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Cryptocurrency> getAll() {
        return cryptoRepository.findAll();
    }

    @Override
    public Cryptocurrency getById(Long id) {
        return cryptoRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Cryptocurrency findBySymbol(String symbol) {
        return cryptoRepository.findBySymbol(symbol);
    }

    @Override
    public void addNewUser(User user) {
        UserCrypto userCrypto = new UserCrypto();
        Cryptocurrency cryptocurrency = findBySymbol(user.getSymbol());
        userCrypto.setPrice_usd(cryptocurrency.getPrice_usd());
        user.setUserCrypto(userCrypto);
        userRepository.save(user);
    }

    @Override
    public void saveCrypto(com.example.rest.demo.restmodel.Cryptocurrency[] url) {

        Cryptocurrency crypto = new Cryptocurrency();

        log.warn("*****URL******");
        for (com.example.rest.demo.restmodel.Cryptocurrency cryp : url) {
            crypto.setId(cryp.getId());
            crypto.setSymbol(cryp.getSymbol());
            crypto.setName(cryp.getName());
            crypto.setNameid(cryp.getNameid());
            crypto.setRank(cryp.getRank());
            crypto.setPrice_usd(cryp.getPrice_usd());
            crypto.setPercent_change_24h(cryp.getPercent_change_24h());
            crypto.setPercent_change_1h(cryp.getPercent_change_1h());
            crypto.setPercent_change_7d(cryp.getPercent_change_7d());
            crypto.setMarket_cap_usd(cryp.getMarket_cap_usd());
            crypto.setVolume24(cryp.getVolume24());
            crypto.setVolume24_native(cryp.getVolume24_native());
            crypto.setCsupply(cryp.getCsupply());
            crypto.setPrice_btc(cryp.getPrice_btc());
            crypto.setTsupply(cryp.getTsupply());
            crypto.setMsupply(cryp.getMsupply());
        }
        cryptoRepository.save(crypto);
    }
}
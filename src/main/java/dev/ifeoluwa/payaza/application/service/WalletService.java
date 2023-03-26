package dev.ifeoluwa.payaza.application.service;

import dev.ifeoluwa.payaza.application.entity.TransactionLogs;
import dev.ifeoluwa.payaza.application.entity.Wallet;
import dev.ifeoluwa.payaza.application.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * @author on 25/03/2023
 * @project
 */

@Service
public class WalletService {

    private WalletRepository walletRepository;

    @Autowired
    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }


    public Wallet saveMoney(Wallet wallet, BigDecimal amount) {
        wallet.setBalance(wallet.getBalance().add(amount).divide(BigDecimal.valueOf(100)));
        return walletRepository.save(wallet);
    }


    public Wallet createWallet() {
        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.valueOf(0.0));
        wallet.setLogsList(new ArrayList<TransactionLogs>());
        walletRepository.save(wallet);
        return wallet;
    }
}

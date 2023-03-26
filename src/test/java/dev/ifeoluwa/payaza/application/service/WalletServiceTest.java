package dev.ifeoluwa.payaza.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.ifeoluwa.payaza.application.entity.TransactionLogs;
import dev.ifeoluwa.payaza.application.entity.Wallet;
import dev.ifeoluwa.payaza.application.repository.WalletRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {WalletService.class})
@ExtendWith(SpringExtension.class)
class WalletServiceTest {
    @MockBean
    private WalletRepository walletRepository;

    @Autowired
    private WalletService walletService;


    @Test
    void testSaveMoney() {
        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.valueOf(42L));
        wallet.setId(123L);
        wallet.setLogsList(new ArrayList<>());
        when(walletRepository.save((Wallet) any())).thenReturn(wallet);

        Wallet wallet1 = new Wallet();
        wallet1.setBalance(BigDecimal.valueOf(42L));
        wallet1.setId(123L);
        wallet1.setLogsList(new ArrayList<>());
        Wallet actualSaveMoneyResult = walletService.saveMoney(wallet1, BigDecimal.valueOf(42L));
        assertSame(wallet, actualSaveMoneyResult);
        assertEquals("42", actualSaveMoneyResult.getBalance().toString());
        verify(walletRepository).save((Wallet) any());
    }


    @Test
    void testCreateWallet() {
        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.valueOf(42L));
        wallet.setId(123L);
        ArrayList<TransactionLogs> transactionLogsList = new ArrayList<>();
        wallet.setLogsList(transactionLogsList);
        when(walletRepository.save((Wallet) any())).thenReturn(wallet);
        Wallet actualCreateWalletResult = walletService.createWallet();
        assertEquals(transactionLogsList, actualCreateWalletResult.getLogsList());
        assertEquals("0.0", actualCreateWalletResult.getBalance().toString());
        verify(walletRepository).save((Wallet) any());
    }
}


package dev.ifeoluwa.payaza.application.service;

import dev.ifeoluwa.payaza.application.dto.VerifyTransactionResponse;
import dev.ifeoluwa.payaza.application.entity.TransactionLogs;
import dev.ifeoluwa.payaza.application.entity.Wallet;
import dev.ifeoluwa.payaza.application.enums.TransactionType;
import dev.ifeoluwa.payaza.application.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author on 25/03/2023
 * @project
 */
@Service
public class TransactionLogsService {

    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionLogsService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }


    public TransactionLogs saveCredit(VerifyTransactionResponse verifyTransactionResponse) {
        TransactionLogs transactionLogs = new TransactionLogs();
        transactionLogs.setTransactionType(TransactionType.CREDIT);
        transactionLogs.setAmount(verifyTransactionResponse.getData().getAmount().divide(BigDecimal.valueOf(100)));
        transactionLogs.setCreatedTime(LocalDateTime.now());
        return transactionRepository.save(transactionLogs);
    }


    public TransactionLogs saveDebit(VerifyTransactionResponse verifyTransactionResponse) {
        TransactionLogs transactionLogs = new TransactionLogs();
        transactionLogs.setTransactionType(TransactionType.DEBIT);
        transactionLogs.setAmount(verifyTransactionResponse.getData().getAmount().divide(BigDecimal.valueOf(100)));
        transactionLogs.setCreatedTime(LocalDateTime.now());
        return transactionRepository.save(transactionLogs);
    }
}


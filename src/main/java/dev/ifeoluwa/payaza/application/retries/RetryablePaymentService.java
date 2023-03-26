package dev.ifeoluwa.payaza.application.retries;

import dev.ifeoluwa.payaza.application.dto.DepositRequest;
import dev.ifeoluwa.payaza.application.dto.DepositResponse;
import dev.ifeoluwa.payaza.application.dto.VerifyTransactionResponse;
import dev.ifeoluwa.payaza.application.exception.TransactionNotVerifiedException;
import dev.ifeoluwa.payaza.application.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

/**
 * @author on 26/03/2023
 * @project
 */
@Service
public class RetryablePaymentService {

    private PaymentService paymentService;

    @Autowired
    public RetryablePaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    @Retryable(
            value = {RuntimeException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 300)
    )
    public DepositResponse retryableInitializeDeposit(DepositRequest depositRequest, String email) {
        return paymentService.initializeDeposit(depositRequest, email);
    }


    @Retryable(
            value = {TransactionNotVerifiedException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 3000)
    )
    public VerifyTransactionResponse retryVerifyTransaction(String reference) {
        return paymentService.verifyTransaction(reference);
    }

}

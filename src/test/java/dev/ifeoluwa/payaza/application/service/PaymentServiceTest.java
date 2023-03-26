package dev.ifeoluwa.payaza.application.service;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import dev.ifeoluwa.payaza.application.dto.DepositRequest;
import dev.ifeoluwa.payaza.application.entity.User;
import dev.ifeoluwa.payaza.application.entity.Wallet;
import dev.ifeoluwa.payaza.application.enums.Roles;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {PaymentService.class})
@ExtendWith(SpringExtension.class)
class PaymentServiceTest {
    @Autowired
    private PaymentService paymentService;

    @MockBean
    private TransactionLogsService transactionLogsService;

    @MockBean
    private UserService userService;

    @MockBean
    private WalletService walletService;


    @Test
    void testVerifyTransaction() {
        assertNull(paymentService.verifyTransaction("Reference"));
        assertNull(paymentService.verifyTransaction("Authorization"));
        assertNull(paymentService.verifyTransaction("42"));
    }

}
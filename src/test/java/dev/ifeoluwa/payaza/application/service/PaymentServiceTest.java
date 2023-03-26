package dev.ifeoluwa.payaza.application.service;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
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
    @Disabled("TODO: Complete this test")
    void testInitializeDeposit5() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.UnsupportedOperationException: Attempted to serialize java.lang.Class: dev.ifeoluwa.payaza.application.dto.DepositRequest. Forgot to register a type adapter?
        //       at com.google.gson.internal.bind.TypeAdapters$1.write(TypeAdapters.java:75)
        //       at com.google.gson.internal.bind.TypeAdapters$1.write(TypeAdapters.java:71)
        //       at com.google.gson.TypeAdapter$1.write(TypeAdapter.java:191)
        //       at com.google.gson.internal.bind.TypeAdapterRuntimeTypeWrapper.write(TypeAdapterRuntimeTypeWrapper.java:69)
        //       at com.google.gson.internal.bind.ReflectiveTypeAdapterFactory$1.write(ReflectiveTypeAdapterFactory.java:126)
        //       at com.google.gson.internal.bind.ReflectiveTypeAdapterFactory$Adapter.write(ReflectiveTypeAdapterFactory.java:244)
        //       at com.google.gson.internal.bind.TypeAdapterRuntimeTypeWrapper.write(TypeAdapterRuntimeTypeWrapper.java:69)
        //       at com.google.gson.internal.bind.ReflectiveTypeAdapterFactory$1.write(ReflectiveTypeAdapterFactory.java:126)
        //       at com.google.gson.internal.bind.ReflectiveTypeAdapterFactory$Adapter.write(ReflectiveTypeAdapterFactory.java:244)
        //       at com.google.gson.internal.bind.TypeAdapterRuntimeTypeWrapper.write(TypeAdapterRuntimeTypeWrapper.java:69)
        //       at com.google.gson.internal.bind.ReflectiveTypeAdapterFactory$1.write(ReflectiveTypeAdapterFactory.java:126)
        //       at com.google.gson.internal.bind.ReflectiveTypeAdapterFactory$Adapter.write(ReflectiveTypeAdapterFactory.java:244)
        //       at com.google.gson.Gson.toJson(Gson.java:747)
        //       at com.google.gson.Gson.toJson(Gson.java:726)
        //       at com.google.gson.Gson.toJson(Gson.java:681)
        //       at com.google.gson.Gson.toJson(Gson.java:661)
        //       at dev.ifeoluwa.payaza.application.service.PaymentService.initializeDeposit(PaymentService.java:75)
        //   In order to prevent initializeDeposit(DepositRequest, String)
        //   from throwing UnsupportedOperationException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   initializeDeposit(DepositRequest, String).
        //   See https://diff.blue/R013 to resolve this issue.

        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.valueOf(Long.MIN_VALUE));
        wallet.setId(123L);
        wallet.setLogsList(new ArrayList<>());

        User user = new User();
        user.setAccountNumber("42");
        user.setEmail("jane.doe@example.org");
        user.setId(123L);
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setRole(Roles.ADMIN);
        user.setWallet(wallet);
        when(userService.getByEmail((String) any())).thenReturn(user);
        DepositRequest depositRequest = mock(DepositRequest.class);
        doNothing().when(depositRequest).setAmount((BigDecimal) any());
        when(depositRequest.getAmount()).thenReturn(BigDecimal.valueOf(42L));
        paymentService.initializeDeposit(depositRequest, "jane.doe@example.org");
    }

    @Test
    void testVerifyTransaction() {
        assertNull(paymentService.verifyTransaction("Reference"));
        assertNull(paymentService.verifyTransaction("Authorization"));
        assertNull(paymentService.verifyTransaction("42"));
    }

}
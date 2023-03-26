package dev.ifeoluwa.payaza.application.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ifeoluwa.payaza.application.dto.DepositRequest;
import dev.ifeoluwa.payaza.application.dto.DepositResponse;
import dev.ifeoluwa.payaza.application.dto.UserDTO;
import dev.ifeoluwa.payaza.application.entity.TransactionLogs;
import dev.ifeoluwa.payaza.application.entity.User;
import dev.ifeoluwa.payaza.application.entity.Wallet;
import dev.ifeoluwa.payaza.application.enums.Roles;
import dev.ifeoluwa.payaza.application.repository.TransactionRepository;
import dev.ifeoluwa.payaza.application.repository.UserRepository;
import dev.ifeoluwa.payaza.application.repository.WalletRepository;
import dev.ifeoluwa.payaza.application.retries.RetryablePaymentService;
import dev.ifeoluwa.payaza.application.service.PaymentService;
import dev.ifeoluwa.payaza.application.service.TransactionLogsService;
import dev.ifeoluwa.payaza.application.service.UserService;
import dev.ifeoluwa.payaza.application.service.WalletService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {PaymentController.class})
@ExtendWith(SpringExtension.class)
class PaymentControllerTest {
    @Autowired
    private PaymentController paymentController;

    @MockBean
    private PaymentService paymentService;

    @MockBean
    private RetryablePaymentService retryablePaymentService;


    @Test
    void testMakeDeposit() throws Exception {
        when(retryablePaymentService.retryableInitializeDeposit((DepositRequest) any(), (String) any()))
                .thenReturn(new DepositResponse());

        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setAmount(BigDecimal.valueOf(42L));
        depositRequest.setEmail("jane.doe@example.org");
        String content = (new ObjectMapper()).writeValueAsString(depositRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/wallet/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(paymentController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;version=1"))
                .andExpect(MockMvcResultMatchers.content().string("{\"status\":false}"));
    }


    @Test
    void testPaymentResponse() throws Exception {
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders
                .formLogin();
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(paymentController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}


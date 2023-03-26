package dev.ifeoluwa.payaza.application.controller;

import dev.ifeoluwa.payaza.application.dto.DepositRequest;
import dev.ifeoluwa.payaza.application.dto.DepositResponse;
import dev.ifeoluwa.payaza.application.entity.User;
import dev.ifeoluwa.payaza.application.retries.RetryablePaymentService;
import dev.ifeoluwa.payaza.application.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

/**
 * @author on 25/03/2023
 * @project
 */

@RestController
@RequestMapping("/api/wallet")
@Slf4j
public class PaymentController {

    private PaymentService paymentService;

    private RetryablePaymentService retryablePaymentService;
    private DepositResponse depositResponse = new DepositResponse();

    @Autowired
    public PaymentController(RetryablePaymentService retryablePaymentService, PaymentService paymentService) {
        this.retryablePaymentService = retryablePaymentService;
        this.paymentService = paymentService;
    }


    @RequestMapping(value = "/deposit", method = RequestMethod.POST, produces = "application/json;version=1")
    public ResponseEntity<?> makeDeposit(@RequestBody DepositRequest request, HttpSession session) {
        User user = (User) session.getAttribute("user");
        depositResponse = retryablePaymentService.retryableInitializeDeposit(request, user.getEmail());
        return new ResponseEntity<>(depositResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/deposit", method = RequestMethod.POST, produces = "application/json;version=2")
    public ResponseEntity<?> makeDepositV2(@RequestParam BigDecimal amount, HttpSession session) {
        User user = (User) session.getAttribute("user");
        DepositRequest request = new DepositRequest(amount, user.getEmail());
        depositResponse = retryablePaymentService.retryableInitializeDeposit(request, user.getEmail());
        return new ResponseEntity<>(depositResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/callback")
    public ResponseEntity<?> paymentResponse() throws Exception {
        return new ResponseEntity<>(retryablePaymentService.retryVerifyTransaction(depositResponse.getData().getReference()),
                HttpStatus.ACCEPTED);

    }
}

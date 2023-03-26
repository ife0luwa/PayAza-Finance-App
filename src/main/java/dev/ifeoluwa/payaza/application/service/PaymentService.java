package dev.ifeoluwa.payaza.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import dev.ifeoluwa.payaza.application.dto.DepositRequest;
import dev.ifeoluwa.payaza.application.dto.DepositResponse;
import dev.ifeoluwa.payaza.application.dto.VerifyTransactionResponse;
import dev.ifeoluwa.payaza.application.entity.TransactionLogs;
import dev.ifeoluwa.payaza.application.entity.User;
import dev.ifeoluwa.payaza.application.entity.Wallet;
import dev.ifeoluwa.payaza.application.exception.TransactionNotVerifiedException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

/**
 * @author on 25/03/2023
 * @project
 */
@Service
public class PaymentService {



    @Value("${secret_key}")
    private String PAYSTACK_SECRET_KEY;

    @Value("${paystack_url}")
    private String PAYSTACK_BASE_URL;

    @Value("${verification_url}")
    private String PAYSTACK_VERIFY_URL;


    private TransactionLogsService transactionLogsService;
    private WalletService walletService;
    private UserService userService;
    private User loggedUser = new User();

    @Autowired
    public PaymentService(TransactionLogsService transactionLogsService, WalletService walletService, UserService userService) {
        this.transactionLogsService = transactionLogsService;
        this.walletService = walletService;
        this.userService = userService;
    }



    public DepositResponse initializeDeposit(DepositRequest depositRequest, String email) {
        User user1 = userService.getByEmail(email);
        loggedUser = user1;

        DepositResponse depositResponse;
        depositRequest.setAmount(depositRequest.getAmount().multiply(BigDecimal.valueOf(100)));

        try {
            Gson gson = new Gson();
            StringEntity entity = new StringEntity(gson.toJson(depositRequest));
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(PAYSTACK_BASE_URL);
            post.setEntity(entity);
            post.addHeader("Content-type", "application/json");
            post.addHeader("Authorization", "Bearer " + PAYSTACK_SECRET_KEY);
            StringBuilder result = new StringBuilder();
            HttpResponse response = httpClient.execute(post);

            if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {

                BufferedReader responseReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line;
                while ((line = responseReader.readLine()) != null) {
                    System.out.println(line);
                    result.append(line);
                }
            } else {
                throw new AuthenticationException("Error Occurred while initializing transaction");
            }
            ObjectMapper mapper = new ObjectMapper();
            depositResponse = mapper.readValue(result.toString(), DepositResponse.class);

        } catch (IOException | AuthenticationException e) {
            throw new RuntimeException("error occurred while initializing transaction");
        }
        return depositResponse;
    }



    public VerifyTransactionResponse verifyTransaction(String reference) {

        VerifyTransactionResponse payStackResponse = null;
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(PAYSTACK_VERIFY_URL + reference);
            request.addHeader("Content-type", "application/json");
            request.addHeader("Authorization", "Bearer " + PAYSTACK_SECRET_KEY);
            StringBuilder result = new StringBuilder();

            HttpResponse response = client.execute(request);

            System.out.println(response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase() + " HERE");
            if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }

            } else {
                throw new TransactionNotVerifiedException("Error Occurred while trying to verify transaction");
            }
            ObjectMapper mapper = new ObjectMapper();


            payStackResponse = mapper.readValue(result.toString(), VerifyTransactionResponse.class);

            if (payStackResponse == null || payStackResponse.getStatus().equals("false")) {
                throw new Exception("An error occurred while  verifying payment");
            } else if (payStackResponse.getData().getStatus().equals("success")) {
                TransactionLogs transactionLog = new TransactionLogs();
                transactionLog = transactionLogsService.saveCredit(payStackResponse);
                Wallet wallet = loggedUser.getWallet();
                wallet.getLogsList().add(transactionLog);
                walletService.saveMoney(loggedUser.getWallet(), payStackResponse.getData().getAmount());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return payStackResponse;
    }




}

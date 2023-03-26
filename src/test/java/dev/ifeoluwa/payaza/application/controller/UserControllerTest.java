package dev.ifeoluwa.payaza.application.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ifeoluwa.payaza.application.dto.AuthRequest;
import dev.ifeoluwa.payaza.application.dto.UserDTO;
import dev.ifeoluwa.payaza.application.entity.User;
import dev.ifeoluwa.payaza.application.entity.Wallet;
import dev.ifeoluwa.payaza.application.enums.Roles;
import dev.ifeoluwa.payaza.application.exception.EmailAlreadyExistsException;
import dev.ifeoluwa.payaza.application.security_configuration.JwtTokenUtil;
import dev.ifeoluwa.payaza.application.service.UserService;

import java.math.BigDecimal;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private GlobalExceptionHandler globalExceptionHandler;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;


    @Test
    void testAuthenticateUser() throws Exception {
        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.valueOf(42L));
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
        when(userService.getUserEntity((Authentication) any())).thenReturn(user);
        when(jwtTokenUtil.generateToken((String) any())).thenReturn("ABC123");
        when(authenticationManager.authenticate((Authentication) any())).thenThrow(new BadCredentialsException("?"));

        AuthRequest authRequest = new AuthRequest();
        authRequest.setEmail("jane.doe@example.org");
        authRequest.setPassword("iloveyou");
        String content = (new ObjectMapper()).writeValueAsString(authRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }


    @Test
    void testUserSignup() throws Exception {
        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.valueOf(42L));
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
        when(userService.createUser((UserDTO) any())).thenReturn(user);

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("jane.doe@example.org");
        userDTO.setName("Name");
        userDTO.setPassword("iloveyou");
        String content = (new ObjectMapper()).writeValueAsString(userDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Registration successful\nwelcome Name"));
    }


}


package dev.ifeoluwa.payaza.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.ifeoluwa.payaza.application.dto.UserDTO;
import dev.ifeoluwa.payaza.application.entity.User;
import dev.ifeoluwa.payaza.application.entity.Wallet;
import dev.ifeoluwa.payaza.application.enums.Roles;
import dev.ifeoluwa.payaza.application.exception.EmailAlreadyExistsException;
import dev.ifeoluwa.payaza.application.repository.UserRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserService.class})
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserServiceTest {
    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @MockBean
    private WalletService walletService;

    /**
     * Method under test: {@link UserService#createUser(UserDTO)}
     */
    @Test
    void testCreateUser() {
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

        Wallet wallet1 = new Wallet();
        wallet1.setBalance(BigDecimal.valueOf(42L));
        wallet1.setId(123L);
        wallet1.setLogsList(new ArrayList<>());

        User user1 = new User();
        user1.setAccountNumber("42");
        user1.setEmail("jane.doe@example.org");
        user1.setId(123L);
        user1.setName("Name");
        user1.setPassword("iloveyou");
        user1.setRole(Roles.ADMIN);
        user1.setWallet(wallet1);
        Optional<User> ofResult = Optional.of(user1);
        when(userRepository.save((User) any())).thenReturn(user);
        when(userRepository.findUserByEmail((String) any())).thenReturn(ofResult);
        assertThrows(EmailAlreadyExistsException.class,
                () -> userService.createUser(new UserDTO("Name", "jane.doe@example.org", "iloveyou")));
        verify(userRepository).findUserByEmail((String) any());
    }

    /**
     * Method under test: {@link UserService#createUser(UserDTO)}
     */
    @Test
    void testCreateUser2() {
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
        when(userRepository.save((User) any())).thenReturn(user);
        when(userRepository.findUserByEmail((String) any())).thenReturn(Optional.empty());

        Wallet wallet1 = new Wallet();
        wallet1.setBalance(BigDecimal.valueOf(42L));
        wallet1.setId(123L);
        wallet1.setLogsList(new ArrayList<>());
        when(walletService.createWallet()).thenReturn(wallet1);
        User actualCreateUserResult = userService.createUser(new UserDTO("Name", "jane.doe@example.org", "iloveyou"));
        Wallet wallet2 = actualCreateUserResult.getWallet();
        assertSame(wallet1, wallet2);
        assertEquals(Roles.USER, actualCreateUserResult.getRole());
        assertEquals("jane.doe@example.org", actualCreateUserResult.getEmail());
        assertEquals("Name", actualCreateUserResult.getName());
        assertEquals("42", wallet2.getBalance().toString());
        verify(userRepository).save((User) any());
        verify(userRepository).findUserByEmail((String) any());
        verify(walletService).createWallet();
    }

    /**
     * Method under test: {@link UserService#createUser(UserDTO)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreateUser3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalArgumentException: rawPassword cannot be null
        //       at org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.encode(BCryptPasswordEncoder.java:107)
        //       at dev.ifeoluwa.payaza.application.service.UserService.createUser(UserService.java:54)
        //   In order to prevent createUser(UserDTO)
        //   from throwing IllegalArgumentException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   createUser(UserDTO).
        //   See https://diff.blue/R013 to resolve this issue.

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
        when(userRepository.save((User) any())).thenReturn(user);
        when(userRepository.findUserByEmail((String) any())).thenReturn(Optional.empty());

        Wallet wallet1 = new Wallet();
        wallet1.setBalance(BigDecimal.valueOf(42L));
        wallet1.setId(123L);
        wallet1.setLogsList(new ArrayList<>());
        when(walletService.createWallet()).thenReturn(wallet1);
        userService.createUser(new UserDTO());
    }

    /**
     * Method under test: {@link UserService#createUser(UserDTO)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreateUser4() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "dev.ifeoluwa.payaza.application.dto.UserDTO.getEmail()" because "userDTO" is null
        //       at dev.ifeoluwa.payaza.application.service.UserService.createUser(UserService.java:48)
        //   In order to prevent createUser(UserDTO)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   createUser(UserDTO).
        //   See https://diff.blue/R013 to resolve this issue.

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
        when(userRepository.save((User) any())).thenReturn(user);
        when(userRepository.findUserByEmail((String) any())).thenReturn(Optional.empty());

        Wallet wallet1 = new Wallet();
        wallet1.setBalance(BigDecimal.valueOf(42L));
        wallet1.setId(123L);
        wallet1.setLogsList(new ArrayList<>());
        when(walletService.createWallet()).thenReturn(wallet1);
        userService.createUser(null);
    }
}


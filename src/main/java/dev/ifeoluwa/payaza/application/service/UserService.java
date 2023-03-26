package dev.ifeoluwa.payaza.application.service;

/**
 * @author on 24/03/2023
 * @project
 */

import dev.ifeoluwa.payaza.application.dto.AuthRequest;
import dev.ifeoluwa.payaza.application.dto.UserDTO;
import dev.ifeoluwa.payaza.application.entity.User;
import dev.ifeoluwa.payaza.application.entity.Wallet;
import dev.ifeoluwa.payaza.application.enums.Roles;
import dev.ifeoluwa.payaza.application.exception.EmailAlreadyExistsException;
import dev.ifeoluwa.payaza.application.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.Optional;

/**
 * @author on 20/01/2023
 * @project
 */
@Service
@Slf4j
public class UserService {

    private UserRepository userRepository;
    private WalletService walletService;
    private PasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepository userRepository, WalletService walletService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.walletService = walletService;
        this.passwordEncoder = passwordEncoder;
    }


    public User createUser(UserDTO userDTO) {
        Optional<User> existingUser = userRepository.findUserByEmail(userDTO.getEmail());
        if(existingUser.isEmpty()) {
            passwordEncoder = new BCryptPasswordEncoder();
            User newUser = new User();
            newUser.setName(userDTO.getName());
            newUser.setEmail(userDTO.getEmail());
            newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            newUser.setRole(Roles.USER);
            newUser.setAccountNumber(generateRandomAccountNumber());

            Wallet wallet = walletService.createWallet();
            newUser.setWallet(wallet);

            userRepository.save(newUser);
            return newUser;
        } else {
            throw new EmailAlreadyExistsException("Email already exists. Try a new email");
        }
    }


    public User getByEmail(String email) {
      Optional<User> user = userRepository.findUserByEmail(email);
      return user.get();
    }


    public static String generateRandomAccountNumber() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] token = new byte[10];
        secureRandom.nextBytes(token);

        StringBuilder builder = new StringBuilder();
        for (byte b : token) {
            int randomNum = Math.abs(b % 10);
            builder.append(randomNum);
        }
        return builder.toString();
    }

    public User getUserEntity(Authentication authentication) {
        org.springframework.security.core.userdetails.User user =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        User entityUser = new User();
        entityUser.setEmail(user.getUsername());
        entityUser.setPassword(user.getPassword());
        return entityUser;
    }

}


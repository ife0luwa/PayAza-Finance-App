package dev.ifeoluwa.payaza.application.controller;

import dev.ifeoluwa.payaza.application.dto.AuthRequest;
import dev.ifeoluwa.payaza.application.dto.AuthResponse;
import dev.ifeoluwa.payaza.application.dto.UserDTO;
import dev.ifeoluwa.payaza.application.entity.User;
import dev.ifeoluwa.payaza.application.exception.EmailAlreadyExistsException;
import dev.ifeoluwa.payaza.application.security_configuration.JwtTokenUtil;
import dev.ifeoluwa.payaza.application.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static org.springframework.http.HttpStatus.CREATED;

/**
 * @author on 24/03/2023
 * @project
 */
@RestController
@RequestMapping(value = "/api/auth")
@Slf4j
public class UserController {

    private UserService userService;
    private JwtTokenUtil jwtTokenUtil;
    private AuthenticationManager authenticationManager;

    private GlobalExceptionHandler handler;


    @Autowired
    public UserController(UserService userService, JwtTokenUtil jwtTokenUtil, AuthenticationManager authenticationManager, GlobalExceptionHandler handler) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
        this.handler = handler;
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<?> userSignup(@RequestBody UserDTO userDTO) {
        try{
            User newUser = userService.createUser(userDTO);
            return new ResponseEntity<>("Registration successful" + "\n" + "welcome " + userDTO.getName(), CREATED);
        } catch (EmailAlreadyExistsException exception) {
            return handler.handleExceptions(exception);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthRequest authRequest, HttpSession session) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getEmail(), authRequest.getPassword()
                    )
            );
            User user = userService.getUserEntity(authentication);

            session.setAttribute("user", user);

            String token = jwtTokenUtil.generateToken(user.getEmail());
            AuthResponse authResponse = new AuthResponse(user.getEmail(), token);
            return ResponseEntity.ok().body(authResponse);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        }
    }



}


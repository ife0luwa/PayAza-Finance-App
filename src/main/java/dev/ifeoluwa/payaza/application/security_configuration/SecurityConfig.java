package dev.ifeoluwa.payaza.application.security_configuration;

import dev.ifeoluwa.payaza.application.enums.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

/**
 * @author on 24/03/2023
 * @project
 */
@Configuration // Marks this as a configuration file
@EnableWebSecurity // Enables security for this application
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // Injecting Dependencies
    private JWTFilter filter;
    private CustomUserDetailsService uds;

    @Autowired
    public SecurityConfig(JWTFilter filter, CustomUserDetailsService uds) {
        this.filter = filter;
        this.uds = uds;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { // Method to configure your app security
        http.csrf().disable() // Disabling csrf
                .httpBasic().disable() // Disabling http basic
                .cors() // Enabling cors
                .and()
                .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll() // Allows auth requests to be made without authentication of any sort
                .antMatchers("/api/wallet/callback").permitAll()
//                .antMatchers("/api/wallet/**").hasAuthority(String.valueOf(Roles.USER))
                .anyRequest().authenticated()
                .and()
                .userDetailsService(uds) // Setting the user details service to the custom implementation
                .exceptionHandling()
                .authenticationEntryPoint(
                        // Rejecting request as unauthorized when entry point is reached
                        // If this point is reached it means that the current request requires authentication
                        // and no JWT token was found attached to the Authorization header of the current request.
                        (request, response, authException) ->
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, " Unauthorized")
                )
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED); // Setting Session to be stateless

        // Adding the JWT filter
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }

    // Creating a bean for the password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Exposing the bean of the authentication manager which will be used to run the authentication process
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}


package dev.ifeoluwa.payaza.application.entity;

import dev.ifeoluwa.payaza.application.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


/**
 * @author on 24/03/2023
 * @project
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(name = "email", nullable = false, unique = true)
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String email;

    @NotBlank
    @Size(min=8, max = 16)
    @Column(name = "password", nullable = false, unique = true)
    private String password;

    @Column(name = "account_number")
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    private Roles role;

    @OneToOne(targetEntity = Wallet.class, cascade = CascadeType.ALL)
    private Wallet wallet;


}

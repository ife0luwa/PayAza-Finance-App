package dev.ifeoluwa.payaza.application.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author on 24/03/2023
 * @project
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private BigDecimal balance;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TransactionLogs> logsList;


}

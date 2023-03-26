package dev.ifeoluwa.payaza.application.entity;

import dev.ifeoluwa.payaza.application.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author on 25/03/2023
 * @project
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionLogs {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private LocalDateTime createdTime;
}

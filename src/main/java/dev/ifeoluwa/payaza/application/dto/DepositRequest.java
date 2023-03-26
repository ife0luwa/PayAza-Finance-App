package dev.ifeoluwa.payaza.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author on 25/03/2023
 * @project
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepositRequest {
    private BigDecimal amount;
    private String email;
}

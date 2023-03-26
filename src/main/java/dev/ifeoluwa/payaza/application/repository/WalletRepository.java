package dev.ifeoluwa.payaza.application.repository;

import dev.ifeoluwa.payaza.application.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author on 20/01/2023
 * @project
 */
public interface WalletRepository  extends JpaRepository<Wallet, Long> {
}

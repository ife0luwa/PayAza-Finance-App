package dev.ifeoluwa.payaza.application.repository;

import dev.ifeoluwa.payaza.application.entity.TransactionLogs;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author on 25/03/2023
 * @project
 */
public interface TransactionRepository extends JpaRepository<TransactionLogs, Long> {

}

package dev.ifeoluwa.payaza.application.repository;

import dev.ifeoluwa.payaza.application.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author on 20/01/2023
 * @project
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);
}

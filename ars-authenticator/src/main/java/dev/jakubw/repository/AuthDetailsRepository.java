package dev.jakubw.repository;

import dev.jakubw.model.AuthDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthDetailsRepository extends JpaRepository<AuthDetailsEntity,String> {
    Optional<AuthDetailsEntity> findByEmail(String email);
    boolean existsByEmailOrUsername(String email, String username);
}

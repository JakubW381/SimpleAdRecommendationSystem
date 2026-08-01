package dev.jakubw.adapter.out.persistance;

import dev.jakubw.domain.model.AdUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdUserPostgresRepository extends JpaRepository<AdUserEntity,String> {
}

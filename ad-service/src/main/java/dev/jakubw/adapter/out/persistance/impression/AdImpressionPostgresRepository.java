package dev.jakubw.adapter.out.persistance.impression;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdImpressionPostgresRepository extends JpaRepository<AdImpressionEntity, String> {
}

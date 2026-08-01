package dev.jakubw.adapter.out.persistance.ad;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdPostgresRepository extends JpaRepository<AdEntity, String> {
    List<AdEntity> findByProviderId(String providerId);

}

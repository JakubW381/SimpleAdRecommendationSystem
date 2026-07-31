package dev.jakubw.adapter.out.persistance.provider;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdProviderPostgresRepository extends JpaRepository<AdProviderEntity, String> {

    @Override
    @Query("""
        SELECT pr FROM AdProviderEntity pr
        LEFT JOIN FETCH pr.ads
        WHERE pr.id = :id
    """)
    Optional<AdProviderEntity> findById(String id);
}

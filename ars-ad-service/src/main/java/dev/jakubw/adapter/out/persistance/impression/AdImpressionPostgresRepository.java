package dev.jakubw.adapter.out.persistance.impression;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdImpressionPostgresRepository extends JpaRepository<AdImpressionEntity, String> {

    @Query("""
        SELECT im FROM AdImpressionEntity im
        JOIN im.ad a
        WHERE a.id = :adId AND a.provider.id = :adProviderId
    """)
    List<AdImpressionEntity> findByAdIdAndAdProviderId(String adId, String adProviderId);
}

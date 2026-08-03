package dev.jakubw.adapter.out.persistance.impression;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AdImpressionPostgresRepository extends JpaRepository<AdImpressionEntity, String> {

    @Query("""
        SELECT im FROM AdImpressionEntity im
        JOIN im.ad a
        WHERE a.id = :adId AND a.provider.id = :adProviderId
    """)
    List<AdImpressionEntity> findByAdIdAndAdProviderId(String adId, String adProviderId);

    @Query("""
        SELECT im FROM AdImpressionEntity im
        JOIN FETCH im.ad
        WHERE im.ad.id IN :adIds AND im.day = :day
    """)
    List<AdImpressionEntity> findByAdIdsAndDay(List<String> adIds, LocalDate day);
}

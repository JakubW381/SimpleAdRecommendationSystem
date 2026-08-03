package dev.jakubw.adapter.out.persistance.ad;

import dev.jakubw.domain.model.AdStatus;
import dev.jakubw.domain.port.out.ad.model.AdRecommendationCandidate;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AdPostgresRepository extends JpaRepository<AdEntity, String> {
    List<AdEntity> findByProviderId(String providerId);


    @Query("""
                SELECT new dev.jakubw.domain.port.out.ad.model.AdRecommendationCandidate(
                           a.id, a.name, a.adUrl, a.tags, a.maxDayCount, coalesce(i.count, 0)
                       )
                FROM AdEntity a
                LEFT JOIN a.impressions i ON i.day = :day
                WHERE a.status = :status
    """)
    List<AdRecommendationCandidate> findRecommendationCandidates(
            @Param("status") AdStatus status,
            @Param("day") LocalDate day
    );


    @Query("""
        SELECT a FROM AdEntity a
        WHERE a.id IN :ids
    """)
    List<AdEntity> findByIdIn(List<String> ids);
}

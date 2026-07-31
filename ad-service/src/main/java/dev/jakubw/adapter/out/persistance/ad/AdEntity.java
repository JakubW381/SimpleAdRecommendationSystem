package dev.jakubw.adapter.out.persistance.ad;

import dev.jakubw.adapter.out.persistance.impression.AdImpressionEntity;
import dev.jakubw.adapter.out.persistance.provider.AdProviderEntity;
import dev.jakubw.domain.model.AdDailyImpression;
import dev.jakubw.domain.model.AdStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@ToString(exclude = {"impressions", "provider"})
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class AdEntity {
    @Id
    private String id;

    @Column(unique = true,nullable = false)
    private String name;
    @Column(nullable = false)
    private String adUrl;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true,mappedBy = "ad")
    @Builder.Default
    private List<AdImpressionEntity> impressions = new ArrayList<>();
    private LocalDate campaignEnd;
    private Long maxDayCount;
    private AdStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    private AdProviderEntity provider;
}

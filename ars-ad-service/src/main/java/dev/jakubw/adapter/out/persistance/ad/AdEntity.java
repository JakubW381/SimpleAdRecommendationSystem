package dev.jakubw.adapter.out.persistance.ad;

import dev.jakubw.adapter.out.persistance.impression.AdImpressionEntity;
import dev.jakubw.adapter.out.persistance.provider.AdProviderEntity;
import dev.jakubw.domain.model.AdStatus;
import dev.jakubw.domain.model.AdTag;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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

    @Enumerated(EnumType.STRING)
    private AdStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    private AdProviderEntity provider;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "tags")
    private Set<AdTag> tags;

    public void addImpression(LocalDate day, long count) {
        AdImpressionEntity impression = new AdImpressionEntity();

        impression.setId(UUID.randomUUID().toString());
        impression.setDay(day);
        impression.setCount(count);
        impression.setAd(this);

        impressions.add(impression);
    }
}

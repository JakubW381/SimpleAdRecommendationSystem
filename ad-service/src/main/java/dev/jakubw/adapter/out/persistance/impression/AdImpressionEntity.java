package dev.jakubw.adapter.out.persistance.impression;

import dev.jakubw.adapter.out.persistance.ad.AdEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString(exclude = "ad")
@EqualsAndHashCode(of = "id")
public class AdImpressionEntity {
    @Id
    private String id;
    private LocalDate day;
    private Long count;

    @ManyToOne(fetch = FetchType.LAZY)
    private AdEntity ad;
}

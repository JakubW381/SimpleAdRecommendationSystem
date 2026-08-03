package dev.jakubw.adapter.out.persistance.impression;

import dev.jakubw.adapter.out.persistance.ad.AdEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString(exclude = "ad")
@EqualsAndHashCode(of = "id")
@Table(
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"ad_id", "day"}
        )
)
@NoArgsConstructor
@AllArgsConstructor
public class AdImpressionEntity {
    @Id
    private String id;
    private LocalDate day;
    private Long count;

    @ManyToOne(fetch = FetchType.LAZY)
    private AdEntity ad;
}

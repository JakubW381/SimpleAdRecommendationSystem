package dev.jakubw.adapter.out.persistance.provider;

import dev.jakubw.adapter.out.persistance.ad.AdEntity;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Persistable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@ToString(exclude = "ads")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class AdProviderEntity implements Persistable<String> {

    @Id
    private String id;
    @Column(unique = true,nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true,mappedBy = "provider")
    @Builder.Default
    private List<AdEntity> ads = new ArrayList<>();
    @Column(nullable = false)
    private LocalDateTime creationDate;

    @Transient
    @Builder.Default
    private boolean isNew = true;

    public void addAd(AdEntity ad){
        ads.add(ad);
        ad.setProvider(this);
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    @PrePersist
    @PostLoad
    public void setNotNew(){
        setNew(false);
    }
}

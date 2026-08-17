package dev.jakubw.adapter.out.persistance;

import dev.jakubw.domain.model.AdTag;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.domain.Persistable;

import java.util.Collections;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class AdUserEntity implements Persistable<String> {

    @Id
    private String id;

    @Column(nullable = false,unique = true)
    private String username;
    @Column(unique = true,nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "tags")
    @Builder.Default
    private Set<AdTag> tags = Collections.emptySet();

    @Transient
    @Builder.Default
    private boolean isNew = true;

    @Override
    public boolean isNew() {
        return false;
    }

    @PostLoad
    @PrePersist
    public void persist(){
        this.isNew = false;
    }
}

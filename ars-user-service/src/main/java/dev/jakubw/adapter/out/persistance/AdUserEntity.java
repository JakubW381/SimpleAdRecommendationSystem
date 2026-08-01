package dev.jakubw.adapter.out.persistance;

import dev.jakubw.domain.model.AdTags;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class AdUserEntity {

    @Id
    private String id;

    @Column(nullable = false,unique = true)
    private String username;
    @Column(unique = true,nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "tags")
    private Set<AdTags> tags;
}

package dev.jakubw.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Persistable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class AuthDetailsEntity implements UserDetails, Persistable<String> {

    @Id
    private String id;

    @Column(unique = true,nullable = false)
    private String username;
    private String email;
    @Column(nullable = false)
    private String password;

    @Enumerated(value = EnumType.STRING)
    private Set<Role> authorities;

    @Transient
    private boolean isNew = true;

    @Override
    public @Nullable String getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
    @PostLoad
    @PrePersist
    public void prePersist(){
        this.isNew = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities.stream()
                .map(role -> (GrantedAuthority) role::name)
                .toList();
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}

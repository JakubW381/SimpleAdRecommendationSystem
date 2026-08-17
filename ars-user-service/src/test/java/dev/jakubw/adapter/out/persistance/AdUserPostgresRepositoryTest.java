package dev.jakubw.adapter.out.persistance;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.UUID;

public class AdUserPostgresRepositoryTest extends BaseRepositoryTest{

    @Autowired
    AdUserPostgresRepository repository;


    @Test
    public void shouldFindById(){
        //  Given
        String username = "test-username";
        String email = "example@test.com";
        String id = UUID.randomUUID().toString();
        AdUserEntity entity = AdUserEntity.builder()
                .id(id)
                .username(username)
                .email(email)
                .build();
        repository.save(entity);
        //  When
        Optional<AdUserEntity> saved = repository.findById(id);

        // Then
        assertTrue(saved.isPresent());
        assertEquals(id,saved.get().getId());
        assertEquals(email,saved.get().getEmail());
        assertEquals(username,saved.get().getUsername());
    }






}

package com.github.saphyra.skyxplore.common;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@RequiredArgsConstructor
public abstract class AbstractEncryptionRepository<T extends Encryptable, ID> {
    protected final JpaRepository<T, ID> repository;
    protected final EncryptionTemplate<T> encryptionTemplate;

    public T save(T entity) {
        return repository.save(encryptionTemplate.encrypt(entity));
    }

    public Optional<T> findById(ID id) {
        return encryptionTemplate.decrypt(repository.findById(id));
    }

    public void delete(T entity) {
        repository.delete(entity);
    }
}

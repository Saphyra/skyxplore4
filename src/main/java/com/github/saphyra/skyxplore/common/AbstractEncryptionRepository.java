package com.github.saphyra.skyxplore.common;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

@RequiredArgsConstructor
public abstract class AbstractEncryptionRepository<T extends Encryptable, ID> extends UnsupportedOperationRepository<T, ID> {
    protected final JpaRepository<T, ID> repository;
    protected final EncryptionTemplate<T> encryptionTemplate;

    @Override
    public <S extends T > S save(S entity) {
        //noinspection unchecked
        return (S) repository.save(encryptionTemplate.encrypt(entity, entity.getKey()));
    }
}

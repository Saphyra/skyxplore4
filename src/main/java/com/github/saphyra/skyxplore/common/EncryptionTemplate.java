package com.github.saphyra.skyxplore.common;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface EncryptionTemplate<T extends Encryptable> {
    T encrypt(T entity);

    default  List<T> encrypt(List<T> entities) {
        return entities.stream()
            .map(this::encrypt)
            .collect(Collectors.toList());
    }

    default Optional<T> encrypt(Optional<T> entity) {
        return entity.map(this::encrypt);
    }

    T decrypt(T entity);

    default  List<T> decrypt(List<T> entities) {
        return entities.stream()
            .map(this::decrypt)
            .collect(Collectors.toList());
    }

    default Optional<T> decrypt(Optional<T> entity) {
        return entity.map(this::decrypt);
    }
}

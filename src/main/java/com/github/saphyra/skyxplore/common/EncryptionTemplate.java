package com.github.saphyra.skyxplore.common;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface EncryptionTemplate<T> {
    T encrypt(T entity, String key);

    default  List<T> encrypt(List<T> entities, String key) {
        return entities.stream()
            .map(s -> encrypt(s, key))
            .collect(Collectors.toList());
    }

    default Optional<T> encrypt(Optional<T> entity, String key) {
        return entity.map(s -> encrypt(s, key));
    }

    T decrypt(T entity, String key);

    default  List<T> decrypt(List<T> entities, String key) {
        return entities.stream()
            .map(s -> decrypt(s, key))
            .collect(Collectors.toList());
    }

    default Optional<T> decrypt(Optional<T> entity, String key) {
        return entity.map(s -> decrypt(s, key));
    }
}

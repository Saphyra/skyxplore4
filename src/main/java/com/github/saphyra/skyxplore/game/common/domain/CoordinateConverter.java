package com.github.saphyra.skyxplore.game.common.domain;

import com.github.saphyra.encryption.impl.IntegerEncryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CoordinateConverter {
    private final IntegerEncryptor integerEncryptor;

    public Coordinate convertEntity(CoordinateEntity coordinateEntity, String userId) {
        return Coordinate.builder()
            .x(integerEncryptor.decryptEntity(coordinateEntity.getX(), userId))
            .y(integerEncryptor.decryptEntity(coordinateEntity.getY(), userId))
            .build();
    }

    public CoordinateEntity convertDomain(Coordinate coordinate, String userId) {
        return CoordinateEntity.builder()
            .x(integerEncryptor.encryptEntity(coordinate.getX(), userId))
            .y(integerEncryptor.encryptEntity(coordinate.getY(), userId))
            .build();
    }
}

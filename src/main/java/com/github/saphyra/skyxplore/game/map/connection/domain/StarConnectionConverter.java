package com.github.saphyra.skyxplore.game.map.connection.domain;

import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.stereotype.Component;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.encryption.impl.StringEncryptor;
import com.github.saphyra.skyxplore.common.UuidConverter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class StarConnectionConverter extends ConverterBase<StarConnectionEntity, StarConnection> {
    private final StringEncryptor stringEncryptor;
    private final UuidConverter uuidConverter;

    @Override
    protected StarConnection processEntityConversion(StarConnectionEntity starConnectionEntity) {
        return StarConnection.builder()
            .connectionId(uuidConverter.convertEntity(starConnectionEntity.getConnectionId()))
            .gameId(uuidConverter.convertEntity(starConnectionEntity.getGameId()))
            .userId(uuidConverter.convertEntity(starConnectionEntity.getUid()))
            .star1(decrypt(starConnectionEntity::getStar1, starConnectionEntity.getUid()))
            .star2(decrypt(starConnectionEntity::getStar2, starConnectionEntity.getUid()))
            .build();
    }

    private UUID decrypt(Supplier<String> stringSupplier, String uid) {
        String decrypted = stringEncryptor.decryptEntity(stringSupplier.get(), uid);
        return uuidConverter.convertEntity(decrypted);
    }

    @Override
    protected StarConnectionEntity processDomainConversion(StarConnection starConnection) {
        return StarConnectionEntity.builder()
            .connectionId(uuidConverter.convertDomain(starConnection.getConnectionId()))
            .gameId(uuidConverter.convertDomain(starConnection.getGameId()))
            .uid(uuidConverter.convertDomain(starConnection.getUserId()))
            .star1(encrypt(starConnection::getStar1, starConnection.getUserId()))
            .star2(encrypt(starConnection::getStar2, starConnection.getUserId()))
            .build();
    }

    private String encrypt(Supplier<UUID> stringSupplier, UUID uid) {
        return stringEncryptor.encryptEntity(
            uuidConverter.convertDomain(stringSupplier.get()),
            uuidConverter.convertDomain(uid)
        );
    }
}

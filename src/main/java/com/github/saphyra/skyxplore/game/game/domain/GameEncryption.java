package com.github.saphyra.skyxplore.game.game.domain;

import com.github.saphyra.encryption.impl.StringEncryptor;
import com.github.saphyra.skyxplore.common.EncryptionTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameEncryption implements EncryptionTemplate<Game> {
    private final StringEncryptor stringEncryptor;

    @Override
    public Game encrypt(Game entity) {
        return entity.toBuilder()
            .gameName(stringEncryptor.encryptEntity(entity.getGameName(), entity.getKey()))
            .build();
    }

    @Override
    public Game decrypt(Game entity) {
        return entity.toBuilder()
            .gameName(stringEncryptor.decryptEntity(entity.getGameName(), entity.getKey()))
            .build();
    }
}

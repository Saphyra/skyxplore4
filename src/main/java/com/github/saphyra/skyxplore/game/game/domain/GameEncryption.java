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
    public Game encrypt(Game entity, String key) {
        return entity.toBuilder()
            .gameName(stringEncryptor.encryptEntity(entity.getGameName(), key))
            .build();
    }

    @Override
    public Game decrypt(Game entity, String key) {
        return entity.toBuilder()
            .gameName(stringEncryptor.decryptEntity(entity.getGameName(), key))
            .build();
    }
}

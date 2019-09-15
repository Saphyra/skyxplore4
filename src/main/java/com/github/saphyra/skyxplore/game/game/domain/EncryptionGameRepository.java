package com.github.saphyra.skyxplore.game.game.domain;

import com.github.saphyra.skyxplore.common.AbstractEncryptionRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EncryptionGameRepository extends AbstractEncryptionRepository<Game, UUID> {
    public EncryptionGameRepository(GameEncryption gameEncryption, GameRepository gameRepository) {
        super(gameRepository, gameEncryption);
    }
}

package com.github.saphyra.skyxplore.game.game.domain;

import com.github.saphyra.skyxplore.common.AbstractEncryptionRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class EncryptionGameRepository extends AbstractEncryptionRepository<Game, UUID> implements GameRepository {
    private final GameRepository gameRepository;

    public EncryptionGameRepository(GameEncryption gameEncryption, GameRepository gameRepository, GameRepository gameRepository1) {
        super(gameRepository, gameEncryption);
        this.gameRepository = gameRepository1;
    }

    @Override
    public List<Game> getByUserId(UUID userId) {
        return encryptionTemplate.decrypt(gameRepository.getByUserId(userId));
    }
}

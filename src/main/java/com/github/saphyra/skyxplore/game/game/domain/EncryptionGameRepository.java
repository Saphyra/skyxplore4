package com.github.saphyra.skyxplore.game.game.domain;

import com.github.saphyra.skyxplore.common.AbstractEncryptionRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class EncryptionGameRepository extends AbstractEncryptionRepository<Game, UUID> {
    private final GameRepository gameRepository;

    public EncryptionGameRepository(GameEncryption gameEncryption, GameRepository gameRepository, GameRepository gameRepository1) {
        super(gameRepository, gameEncryption);
        this.gameRepository = gameRepository1;
    }

    public List<Game> getByUserId(UUID userId) {
        return encryptionTemplate.decrypt(gameRepository.getByUserId(userId));
    }

    public Optional<Game> findByGameIdAndUserId(UUID gameId, UUID userId) {
        return encryptionTemplate.decrypt(gameRepository.findByGameIdAndUserId(gameId, userId));
    }
}

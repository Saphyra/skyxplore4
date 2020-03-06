package com.github.saphyra.skyxplore.app.domain.player;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PlayerCommandServiceTest {
    private static final UUID GAME_ID = UUID.randomUUID();

    @Mock
    private PlayerDao playerDao;

    @InjectMocks
    private PlayerCommandService underTest;

    @Mock
    private Player player;

    @Test
    public void delete() {
        underTest.delete(player);

        verify(playerDao).delete(player);
    }

    @Test
    public void deleteAll() {
        underTest.deleteAll(Arrays.asList(player));

        verify(playerDao).deleteAll(Arrays.asList(player));
    }

    @Test
    public void deleteByGameId() {
        underTest.deleteByGameId(GAME_ID);

        verify(playerDao).deleteByGameId(GAME_ID);
    }

    @Test
    public void save() {
        underTest.save(player);

        verify(playerDao).save(player);
    }

    @Test
    public void saveAll() {
        underTest.saveAll(Arrays.asList(player));

        verify(playerDao).saveAll(Arrays.asList(player));
    }

    @Test
    public void getType() {
        Class<Player> result = underTest.getType();

        assertThat(result).isEqualTo(Player.class);
    }
}
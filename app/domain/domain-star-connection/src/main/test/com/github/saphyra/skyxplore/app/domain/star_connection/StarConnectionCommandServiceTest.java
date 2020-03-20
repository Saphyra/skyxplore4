package com.github.saphyra.skyxplore.app.domain.star_connection;

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
public class StarConnectionCommandServiceTest {
    private static final UUID GAME_ID = UUID.randomUUID();

    @Mock
    private StarConnectionDao starConnectionDao;

    @InjectMocks
    private StarConnectionCommandService underTest;

    @Mock
    private StarConnection starConnection;

    @Test
    public void delete() {
        underTest.delete(starConnection);

        verify(starConnectionDao).delete(starConnection);
    }

    @Test
    public void deleteAll() {
        underTest.deleteAll(Arrays.asList(starConnection));

        verify(starConnectionDao).deleteAll(Arrays.asList(starConnection));
    }

    @Test
    public void deleteByGameId() {
        underTest.deleteByGameId(GAME_ID);

        verify(starConnectionDao).deleteByGameId(GAME_ID);
    }

    @Test
    public void save() {
        underTest.save(starConnection);

        verify(starConnectionDao).save(starConnection);
    }

    @Test
    public void saveAll() {
        underTest.saveAll(Arrays.asList(starConnection));

        verify(starConnectionDao).saveAll(Arrays.asList(starConnection));
    }

    @Test
    public void getType() {
        Class<StarConnection> result = underTest.getType();

        assertThat(result).isEqualTo(StarConnection.class);
    }
}
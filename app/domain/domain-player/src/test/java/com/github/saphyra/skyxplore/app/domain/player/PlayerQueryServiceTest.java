package com.github.saphyra.skyxplore.app.domain.player;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.skyxplore.app.common.exception_handling.ErrorCode;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContext;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContextHolder;

@RunWith(MockitoJUnitRunner.class)
public class PlayerQueryServiceTest {
    private static final UUID GAME_ID = UUID.randomUUID();
    private static final UUID PLAYER_ID = UUID.randomUUID();
    @Mock
    private PlayerDao playerDao;

    @Mock
    private RequestContextHolder requestContextHolder;

    @InjectMocks
    private PlayerQueryService underTest;

    @Mock
    private RequestContext requestContext;

    @Mock
    private Player player;

    @Before
    public void setUp() {
        given(requestContextHolder.get()).willReturn(requestContext);
        given(requestContext.getPlayerId()).willReturn(PLAYER_ID);
        given(requestContext.getGameId()).willReturn(GAME_ID);

        given(player.getPlayerId()).willReturn(PLAYER_ID);
    }

    @Test
    public void findByGameIdAndPlayerId() {
        given(playerDao.findPlayerByGameIdAndPlayerId(GAME_ID, PLAYER_ID)).willReturn(Optional.of(player));

        Optional<Player> result = underTest.findByGameIdAndPlayerId();

        assertThat(result).contains(player);
    }

    @Test
    public void findByGameIdAndPlayerId_notFound() {
        given(playerDao.findPlayerByGameIdAndPlayerId(GAME_ID, PLAYER_ID)).willReturn(Optional.empty());

        Optional<Player> result = underTest.findByGameIdAndPlayerId();

        assertThat(result).isEmpty();
    }

    @Test
    public void findPlayerIdByUserIdAndGameId_found() {
        given(player.isAi()).willReturn(false);

        given(playerDao.getByGameId(GAME_ID)).willReturn(Arrays.asList(player));

        UUID result = underTest.findPlayerIdByUserIdAndGameId(GAME_ID);

        assertThat(result).isEqualTo(PLAYER_ID);
    }

    @Test
    public void findPlayerIdByUserIdAndGameId_ai() {
        given(player.isAi()).willReturn(true);

        given(playerDao.getByGameId(GAME_ID)).willReturn(Arrays.asList(player));

        Throwable ex = catchThrowable(() -> underTest.findPlayerIdByUserIdAndGameId(GAME_ID));

        assertThat(ex).isInstanceOf(NotFoundException.class);
        NotFoundException exception = (NotFoundException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.PLAYER_NOT_FOUND.name());
    }

    @Test
    public void getByGameId() {
        given(playerDao.getByGameId(GAME_ID)).willReturn(Arrays.asList(player));

        List<Player> result = underTest.getByGameId();

        assertThat(result).containsExactly(player);
    }
}
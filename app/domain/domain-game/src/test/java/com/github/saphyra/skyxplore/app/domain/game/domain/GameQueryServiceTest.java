package com.github.saphyra.skyxplore.app.domain.game.domain;

import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.skyxplore.app.common.exception_handling.ErrorCode;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContext;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContextHolder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class GameQueryServiceTest {
    private static final UUID USER_ID = UUID.randomUUID();
    private static final UUID GAME_ID = UUID.randomUUID();

    @Mock
    private GameDao gameDao;

    @Mock
    private RequestContextHolder requestContextHolder;

    @InjectMocks
    private GameQueryService underTest;

    @Mock
    private RequestContext requestContext;

    @Mock
    private Game game;

    @Before
    public void setUp() {
        given(requestContextHolder.get()).willReturn(requestContext);
        given(requestContext.getUserId()).willReturn(USER_ID);
        given(requestContext.getGameId()).willReturn(GAME_ID);
    }

    @Test
    public void findByGameIdAndUserIdValidated_found() {
        given(gameDao.findByGameIdAndUserId(GAME_ID, USER_ID)).willReturn(Optional.of(game));

        Game result = underTest.findByGameIdAndUserIdValidated();

        assertThat(result).isEqualTo(game);
    }

    @Test
    public void findByGameIdAndUserIdValidated_notFound() {
        given(gameDao.findByGameIdAndUserId(GAME_ID, USER_ID)).willReturn(Optional.empty());

        Throwable ex = catchThrowable(() -> underTest.findByGameIdAndUserIdValidated());

        assertThat(ex).isInstanceOf(NotFoundException.class);
        NotFoundException exception = (NotFoundException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.GAME_NOT_FOUND.name());
    }

    @Test
    public void findByGameIdAndUserId_found() {
        given(gameDao.findByGameIdAndUserId(GAME_ID, USER_ID)).willReturn(Optional.of(game));

        Optional<Game> result = underTest.findByGameIdAndUserId(GAME_ID);

        assertThat(result).contains(game);
    }

    @Test
    public void findByGameIdAndUserId_notFound() {
        given(gameDao.findByGameIdAndUserId(GAME_ID, USER_ID)).willReturn(Optional.empty());

        Optional<Game> result = underTest.findByGameIdAndUserId(GAME_ID);

        assertThat(result).isEmpty();
    }

    @Test
    public void getByUserId() {
        given(gameDao.getByUserId(USER_ID)).willReturn(Arrays.asList(game));

        List<Game> result = underTest.getByUserId();

        assertThat(result).containsExactly(game);
    }
}
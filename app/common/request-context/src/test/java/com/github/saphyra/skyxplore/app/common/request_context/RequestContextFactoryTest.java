package com.github.saphyra.skyxplore.app.common.request_context;

import com.github.saphyra.skyxplore.common.config.RequestConstants;
import com.github.saphyra.util.CookieUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class RequestContextFactoryTest {
    private static final UUID USER_ID = UUID.randomUUID();
    private static final UUID GAME_ID = UUID.randomUUID();
    private static final UUID PLAYER_ID = UUID.randomUUID();
    @Mock
    private CookieUtil cookieUtil;

    @InjectMocks
    private RequestContextFactory underTest;

    @Mock
    private HttpServletRequest request;

    @Test
    public void createContext() {
        //GIVEN
        given(cookieUtil.getCookie(request, RequestConstants.COOKIE_USER_ID)).willReturn(Optional.of(USER_ID.toString()));
        given(cookieUtil.getCookie(request, RequestConstants.COOKIE_GAME_ID)).willReturn(Optional.of(GAME_ID.toString()));
        given(cookieUtil.getCookie(request, RequestConstants.COOKIE_PLAYER_ID)).willReturn(Optional.of(PLAYER_ID.toString()));
        //WHEN
        RequestContext result = underTest.createContext(request);
        //THEN
        assertThat(result.getUserId()).isEqualTo(USER_ID);
        assertThat(result.getGameId()).isEqualTo(GAME_ID);
        assertThat(result.getPlayerId()).isEqualTo(PLAYER_ID);
    }
}
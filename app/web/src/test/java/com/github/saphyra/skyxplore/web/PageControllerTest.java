package com.github.saphyra.skyxplore.web;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.common.config.RequestConstants;
import com.github.saphyra.util.CookieUtil;

@RunWith(MockitoJUnitRunner.class)
public class PageControllerTest {
    @Mock
    private CookieUtil cookieUtil;

    @InjectMocks
    private PageController underTest;

    @Mock
    private HttpServletResponse response;

    @Test
    public void mainMenu_shouldClearCookies() {
        //WHEN
        underTest.mainMenu(response);
        //THEN
        cookieUtil.setCookie(response, RequestConstants.COOKIE_PLAYER_ID, "", 0);
        cookieUtil.setCookie(response, RequestConstants.COOKIE_GAME_ID, "", 0);
    }
}
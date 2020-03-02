package com.github.saphyra.skyxplore.test.frontend.game;

import static org.assertj.core.api.Assertions.assertThat;

import org.openqa.selenium.WebDriver;

import com.github.saphyra.skyxplore.app.common.config.RequestConstants;
import com.github.saphyra.skyxplore.test.framework.VerifiedOperation;

public class GamePageActions {
    public static void leaveGame(WebDriver driver) {
        assertThat(driver.getCurrentUrl()).contains(RequestConstants.GAME_MAPPING_BASE);

        GamePage.exitButton(driver).click();

        VerifiedOperation.waitUntil(() -> driver.getCurrentUrl().endsWith(RequestConstants.MAIN_MENU_MAPPING));
        assertThat(driver.getCurrentUrl()).endsWith(RequestConstants.MAIN_MENU_MAPPING);
    }
}

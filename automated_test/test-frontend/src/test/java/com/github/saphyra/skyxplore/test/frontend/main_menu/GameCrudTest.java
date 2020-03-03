package com.github.saphyra.skyxplore.test.frontend.main_menu;

import com.github.saphyra.skyxplore.app.common.config.RequestConstants;
import com.github.saphyra.skyxplore.test.common.parameters.RegistrationParameters;
import com.github.saphyra.skyxplore.test.framework.Navigation;
import com.github.saphyra.skyxplore.test.framework.NotificationUtil;
import com.github.saphyra.skyxplore.test.framework.VerifiedOperation;
import com.github.saphyra.skyxplore.test.frontend.SeleniumTest;
import com.github.saphyra.skyxplore.test.frontend.game.GamePageActions;
import com.github.saphyra.skyxplore.test.frontend.index.IndexPageActions;
import com.github.saphyra.skyxplore.test.frontend.main_menu.game_crud.GameNameValidationResult;
import com.github.saphyra.skyxplore.test.frontend.main_menu.game_crud.GameViewResult;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class GameCrudTest extends SeleniumTest {
    private static final String TOO_SHORT_GAME_NAME = "ga";
    private static final String TOO_LONG_GAME_NAME = Stream.generate(() -> "a").limit(31).collect(Collectors.joining());
    private static final String GAME_NAME = "game-name";

    @DataProvider(name = "gameCreationParameters", parallel = true)
    public Object[][] gameCreationParameters() {
        return new Object[][]{
            {TOO_SHORT_GAME_NAME, GameNameValidationResult.GAME_NAME_TOO_SHORT},
            {TOO_LONG_GAME_NAME, GameNameValidationResult.GAME_NAME_TOO_LONG}
        };
    }

    @Test(dataProvider = "gameCreationParameters")
    public void verifyValidation(String gameName, GameNameValidationResult result) throws InterruptedException {
        WebDriver driver = extractDriver();
        Navigation.toIndexPage(driver, PORT);

        RegistrationParameters user = RegistrationParameters.validParameters();
        IndexPageActions.registerUser(driver, user);

        MainMenuPageActions.fillGameName(driver, gameName);
        Thread.sleep(2000);

        MainMenuPageActions.verifyGameNameForm(driver, result);
    }

    @Test
    public void createGame() throws InterruptedException {
        WebDriver driver = extractDriver();
        Navigation.toIndexPage(driver, PORT);

        RegistrationParameters user = RegistrationParameters.validParameters();
        IndexPageActions.registerUser(driver, user);

        MainMenuPageActions.fillGameName(driver, GAME_NAME);
        MainMenuPageActions.submitGameCreationForm(driver);

        VerifiedOperation.waitUntil(() -> driver.getCurrentUrl().contains(RequestConstants.GAME_MAPPING_BASE));
        assertThat(driver.getCurrentUrl()).contains(RequestConstants.GAME_MAPPING_BASE);

        NotificationUtil.verifySuccessNotification(driver, "Játék létrehozva");

        GamePageActions.leaveGame(driver);

        List<GameViewResult> games = MainMenuPageActions.getGames(driver, 1);
        assertThat(games.stream().anyMatch(gameViewResult -> gameViewResult.getGameName().equals(GAME_NAME))).isTrue();
    }

    @Test
    public void deleteGame() {
        WebDriver driver = extractDriver();
        Navigation.toIndexPage(driver, PORT);

        RegistrationParameters user = RegistrationParameters.validParameters();
        IndexPageActions.registerUser(driver, user);

        MainMenuPageActions.createGame(driver, GAME_NAME);
        GamePageActions.leaveGame(driver);

        GameViewResult game = MainMenuPageActions.findGameByName(driver, GAME_NAME);
        game.delete();

        NotificationUtil.verifySuccessNotification(driver, "Játék törölve.");
        assertThat(MainMenuPageActions.getGames(driver, 0)).isEmpty();
    }
}

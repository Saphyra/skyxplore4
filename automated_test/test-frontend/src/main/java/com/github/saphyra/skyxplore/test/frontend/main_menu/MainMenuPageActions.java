package com.github.saphyra.skyxplore.test.frontend.main_menu;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.saphyra.skyxplore.app.common.config.RequestConstants;
import com.github.saphyra.skyxplore.test.framework.Fetcher;
import com.github.saphyra.skyxplore.test.framework.NotificationUtil;
import com.github.saphyra.skyxplore.test.framework.Operation;
import com.github.saphyra.skyxplore.test.framework.VerifiedOperation;
import com.github.saphyra.skyxplore.test.framework.WebElementUtils;
import com.github.saphyra.skyxplore.test.frontend.main_menu.game_crud.GameNameValidationResult;
import com.github.saphyra.skyxplore.test.frontend.main_menu.game_crud.GameViewResult;

public class MainMenuPageActions {
    public static void logout(WebDriver driver) {
        assertThat(driver.getCurrentUrl()).endsWith("/main-menu");

        VerifiedOperation.operate(
            new Operation() {
                @Override
                public void execute() {
                    MainMenuPage.logoutButton(driver).click();
                }

                @Override
                public boolean check() {
                    return driver.getCurrentUrl().endsWith(RequestConstants.INDEX_MAPPING);
                }
            },
            10,
            100
        );

        NotificationUtil.verifySuccessNotification(driver, "Sikeres kijelentkezés.");
    }

    public static void createGame(WebDriver driver, String gameName) {
        fillGameName(driver, gameName);
        VerifiedOperation.waitUntil(() -> MainMenuPage.createGameButton(driver).isEnabled());
        submitGameCreationForm(driver);

        VerifiedOperation.waitUntil(() -> driver.getCurrentUrl().contains(RequestConstants.GAME_MAPPING_BASE), 60, 1000);
    }

    public static void fillGameName(WebDriver driver, String gameName) {
        assertThat(driver.getCurrentUrl()).endsWith(RequestConstants.MAIN_MENU_MAPPING);

        WebElementUtils.clearAndFill(MainMenuPage.gameNameInput(driver), gameName);
    }

    public static void verifyGameNameForm(WebDriver driver, GameNameValidationResult errorMessage) {
        assertThat(MainMenuPage.invalidGameName(driver).getAttribute("title")).isEqualTo(errorMessage.getErrorMessage());

        assertThat(MainMenuPage.createGameButton(driver).isEnabled()).isEqualTo(false);
    }

    public static void submitGameCreationForm(WebDriver driver) {
        VerifiedOperation.operate(
            new Operation() {
                @Override
                public void execute() {
                    WebElement submitButton = MainMenuPage.createGameButton(driver);
                    VerifiedOperation.waitUntil(submitButton::isEnabled, 10, 500);
                    assertThat(submitButton.isEnabled()).isTrue();

                    submitButton.click();
                }

                @Override
                public boolean check() {
                    return driver.getCurrentUrl().contains(RequestConstants.GAME_MAPPING_BASE);
                }
            }
        );
    }

    public static GameViewResult findGameByName(WebDriver driver, String gameName) {
        return getGames(driver, 1)
            .stream()
            .filter(gameViewResult -> gameViewResult.getGameName().equals(gameName))
            .findAny()
            .orElseThrow(() -> new RuntimeException("Game not found with name " + gameName));
    }

    public static List<GameViewResult> getGames(WebDriver driver, int minimumSize) {
        List<WebElement> games = VerifiedOperation.getWithWait(new Fetcher<List<WebElement>>() {
            @Override
            public List<WebElement> fetch() {
                return MainMenuPage.getGames(driver);
            }

            @Override
            public boolean check() {
                return fetch().size() >= minimumSize;
            }
        });

        return games.stream()
            .map(webElement -> new GameViewResult(driver, webElement))
            .collect(Collectors.toList());
    }
}

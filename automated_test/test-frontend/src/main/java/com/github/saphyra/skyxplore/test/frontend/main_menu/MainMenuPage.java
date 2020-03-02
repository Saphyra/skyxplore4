package com.github.saphyra.skyxplore.test.frontend.main_menu;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MainMenuPage {
    private static final By LOGOUT_BUTTON = By.id("logout-button");
    private static final By GAME_NAME_INPUT = By.id("new-game-name");
    private static final By INVALID_GAME_NAME = By.id("invalid-new-game-name");
    private static final By CREATE_GAME_BUTTON = By.id("new-game-button");
    private static final By GET_GAMES = By.cssSelector("#games tr");

    public static WebElement logoutButton(WebDriver driver) {
        return driver.findElement(LOGOUT_BUTTON);
    }

    public static WebElement gameNameInput(WebDriver driver) {
        return driver.findElement(GAME_NAME_INPUT);
    }

    public static WebElement invalidGameName(WebDriver driver) {
        return driver.findElement(INVALID_GAME_NAME);
    }

    public static WebElement createGameButton(WebDriver driver) {
        return driver.findElement(CREATE_GAME_BUTTON);
    }

    public static List<WebElement> getGames(WebDriver driver) {
        return driver.findElements(GET_GAMES);
    }
}

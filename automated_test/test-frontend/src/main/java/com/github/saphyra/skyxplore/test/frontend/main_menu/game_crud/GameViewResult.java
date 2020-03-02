package com.github.saphyra.skyxplore.test.frontend.main_menu.game_crud;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import lombok.Data;
import lombok.NonNull;

@Data
public class GameViewResult {
    private static final By GET_GAME_NAME = By.cssSelector("td:first-child");
    private static final By DELETE_BUTTON = By.cssSelector("td:last-child button");

    @NonNull
    private final WebDriver driver;

    @NonNull
    private final WebElement webElement;

    public String getGameName() {
        return webElement.findElement(GET_GAME_NAME).getText();
    }

    public void delete() {
        webElement.findElement(DELETE_BUTTON).click();
        driver.switchTo().alert().accept();
    }
}

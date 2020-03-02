package com.github.saphyra.skyxplore.test.frontend.game;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GamePage {
    private static final By EXIT_BUTTON = By.id("exit");

    public static WebElement exitButton(WebDriver driver) {
        return driver.findElement(EXIT_BUTTON);
    }
}

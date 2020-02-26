package com.github.saphyra.skyxplore.test.frontend.main_menu;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MainMenuPage {
    private static final By LOGOUT_BUTTON = By.id("logout-button");

    public static WebElement logoutButton(WebDriver driver) {
        return driver.findElement(LOGOUT_BUTTON);
    }
}

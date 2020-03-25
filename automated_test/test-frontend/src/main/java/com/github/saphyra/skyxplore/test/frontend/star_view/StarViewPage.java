package com.github.saphyra.skyxplore.test.frontend.star_view;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class StarViewPage {
    private static final By CONTAINER = By.className("star-view-container");
    private static final By GET_STAR_NAME_FIELD = By.className("star-view-star-name");
    private static final By GET_CLOSE_PAGE_BUTTON = By.cssSelector(".star-view-container .close-button");

    public static boolean isOpened(WebDriver driver) {
        try {
            return driver.findElement(CONTAINER).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public static WebElement getStarNameField(WebDriver driver) {
        return driver.findElement(GET_STAR_NAME_FIELD);
    }

    public static WebElement getClosePageButton(WebDriver driver) {
        return driver.findElement(GET_CLOSE_PAGE_BUTTON);
    }
}

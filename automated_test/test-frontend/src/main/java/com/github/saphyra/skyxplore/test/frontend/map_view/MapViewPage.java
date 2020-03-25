package com.github.saphyra.skyxplore.test.frontend.map_view;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MapViewPage {
    private static final By GET_STARS = By.cssSelector("#map-elements-container > .star-element");
    private static final By GET_STAR_NAME_LABELS = By.cssSelector("#map-elements-container > .star-name-element");

    public static List<WebElement> getStars(WebDriver driver) {
        return driver.findElements(GET_STARS);
    }

    public static List<WebElement> getStarNameLabels(WebDriver driver) {
        return driver.findElements(GET_STAR_NAME_LABELS);
    }
}

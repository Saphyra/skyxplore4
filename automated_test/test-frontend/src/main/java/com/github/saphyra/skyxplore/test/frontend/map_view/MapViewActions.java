package com.github.saphyra.skyxplore.test.frontend.map_view;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.saphyra.skyxplore.test.framework.VerifiedOperation;
import com.github.saphyra.skyxplore.test.frontend.star_view.StarViewPage;

public class MapViewActions {
    public static void openStar(WebDriver driver, WebElement starElement) {
        starElement.click();
        boolean result = VerifiedOperation.waitUntil(() -> StarViewPage.isOpened(driver));
        assertThat(result).isTrue();
    }

    public static WebElement getLabelForStar(WebDriver driver, WebElement star) {
        int x = Integer.parseInt(star.getAttribute("cx"));
        int y = Integer.parseInt(star.getAttribute("cy")) - 30;

        List<WebElement> starNameLabels = MapViewPage.getStarNameLabels(driver);
        return starNameLabels.stream()
            .filter(webElement -> Integer.parseInt(webElement.getAttribute("x")) == x)
            .filter(webElement -> Integer.parseInt(webElement.getAttribute("y")) == y)
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Label not found."));
    }
}

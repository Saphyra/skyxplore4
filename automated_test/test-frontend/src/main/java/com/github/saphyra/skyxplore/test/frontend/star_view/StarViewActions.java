package com.github.saphyra.skyxplore.test.frontend.star_view;

import static org.assertj.core.api.Assertions.assertThat;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.saphyra.skyxplore.test.framework.Operation;
import com.github.saphyra.skyxplore.test.framework.VerifiedOperation;

public class StarViewActions {
    public static void modifyStarName(WebDriver driver, String starName) {
        WebElement starNameField = StarViewPage.getStarNameField(driver);
        starNameField.click();
        boolean result = VerifiedOperation.waitUntil(() -> starNameField.getAttribute("contenteditable").equals(String.valueOf(true)));
        assertThat(result).isTrue();
        do {
            starNameField.sendKeys(Keys.BACK_SPACE);
        } while (!starNameField.getText().isEmpty());

        starNameField.sendKeys(starName);

        driver.findElement(By.tagName("footer")).click();
    }

    public static void closePage(WebDriver driver) {
        VerifiedOperation.operate(new Operation() {
            @Override
            public void execute() {
                StarViewPage.getClosePageButton(driver).click();
            }

            @Override
            public boolean check() {
                return !StarViewPage.isOpened(driver);
            }
        });
    }
}

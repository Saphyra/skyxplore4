package com.github.saphyra.skyxplore.test.framework;

import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotificationUtil {
    private static final By NOTIFICATIONS_LOCATOR = By.cssSelector("#notification-container > DIV");
    private static final By NOTIFICATION_TEXT_LOCATOR = By.cssSelector(":first-child");

    public static void verifyErrorNotification(WebDriver driver, String notificationMessage) {
        waitUntilNotificationVisible(driver, notificationMessage);
        Optional<WebElement> matchingNotification = getMatchingNotification(driver, notificationMessage);
        WebElement element = matchingNotification.get()
            .findElement(NOTIFICATION_TEXT_LOCATOR);
        String backgroundColor = element.getCssValue("backgroundColor");
        if (!backgroundColor.equals("rgba(255, 0, 0, 1)")) {
            throw new AssertionError("Notification's background color is not red. It is " + backgroundColor);
        }
    }

    public static void verifySuccessNotification(WebDriver driver, String notificationMessage) {
        waitUntilNotificationVisible(driver, notificationMessage);
        Optional<WebElement> matchingNotification = getMatchingNotification(driver, notificationMessage);
        WebElement element = matchingNotification.get()
            .findElement(NOTIFICATION_TEXT_LOCATOR);
        String backgroundColor = element.getCssValue("backgroundColor");
        if (!backgroundColor.equals("rgba(0, 128, 0, 1)")) {
            throw new AssertionError("Notification's background color is not green. It is " + backgroundColor);
        }
    }

    private static void waitUntilNotificationVisible(WebDriver driver, String notificationMessage) {
        VerifiedOperation.waitUntil(
            () -> driver.findElements(NOTIFICATIONS_LOCATOR).stream()
                .anyMatch(webElement -> webElement.findElement(NOTIFICATION_TEXT_LOCATOR).getText().equals(notificationMessage))
        );
    }

    private static Optional<WebElement> getMatchingNotification(WebDriver driver, String notificationMessage) {
        Optional<WebElement> matchingNotification = driver.findElements(NOTIFICATIONS_LOCATOR).stream()
            .peek(webElement -> log.info("Notification found with message {}", webElement.findElement(NOTIFICATION_TEXT_LOCATOR).getText()))
            .filter(webElement -> webElement.findElement(NOTIFICATION_TEXT_LOCATOR).getText().equals(notificationMessage))
            .findAny();
        if (!matchingNotification.isPresent()) {
            throw new AssertionError("No notification matches notificationMessage " + notificationMessage);
        }
        return matchingNotification;
    }
}

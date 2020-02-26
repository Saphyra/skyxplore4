package com.github.saphyra.skyxplore.test.frontend;

import static java.util.Objects.isNull;

import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.github.saphyra.skyxplore.server.Application;
import com.github.saphyra.skyxplore.test.common.TestBase;
import com.github.saphyra.skyxplore.test.framework.SleepUtil;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SeleniumTest extends TestBase {
    private static final String CHROME_DRIVER_PROPERTY_NAME = "webdriver.chrome.driver";
    private static final String CHROME_DRIVER_EXE_LOCATION = "chromedriver.exe";
    private static final boolean HEADLESS_MODE = false;

    private ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    @BeforeMethod(alwaysRun = true)
    public void startDriver() {
        if (isNull(Application.applicationContext)) {
            throw new SkipException("Application is not started.");
        }

        String chromeDriverLocation = getClass()
            .getClassLoader()
            .getResource(CHROME_DRIVER_EXE_LOCATION)
            .getPath();
        System.setProperty(CHROME_DRIVER_PROPERTY_NAME, chromeDriverLocation);
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(HEADLESS_MODE);
        options.addArguments("window-size=1920,1080");

        ChromeDriver driver = new ChromeDriver(options);
        log.info("Driver created: {}", driver);
        this.driver.set(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void stopDriver(ITestResult testResult) {
        WebDriver driver = this.driver.get();
        if (ITestResult.FAILURE == testResult.getStatus() && !HEADLESS_MODE) {
            extractLogs(driver);
            SleepUtil.sleep(20000);
        }
        if (!isNull(driver)) {
            log.info("Closing driver {}", driver);
            driver.close();
            driver.quit();
        }
    }

    protected WebDriver extractDriver() {
        return Optional.ofNullable(driver.get())
            .orElseThrow(() -> new RuntimeException("WebDriver has not been initialized."));
    }


    private void extractLogs(WebDriver driver) {
        log.info("Extracting logs...");
        driver.findElements(By.cssSelector("#logcontainermessages > div"))
            .stream()
            .map(this::extractMessage)
            .forEach(logMessage -> log.info(logMessage.toString()));
        log.info("Logs extracted.");
    }

    private LogMessage extractMessage(WebElement webElement) {
        return LogMessage.builder()
            .severity(webElement.findElement(By.cssSelector(":first-child")).getText())
            .title(webElement.findElement(By.cssSelector(":nth-child(2)")).getText())
            .message(webElement.findElement(By.cssSelector(":nth-child(3)")).getText())
            .build();
    }

    @Data
    @Builder
    private static class LogMessage {
        private final String severity;
        private final String title;
        private final String message;

        @Override
        public String toString() {
            return String.format("%s --- %s - %s", severity, title, message);
        }
    }
}

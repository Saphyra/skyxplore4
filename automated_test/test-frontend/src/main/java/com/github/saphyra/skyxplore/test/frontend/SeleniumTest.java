package com.github.saphyra.skyxplore.test.frontend;

import com.github.saphyra.skyxplore.server.Application;
import com.github.saphyra.skyxplore.test.common.TestBase;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.Optional;

import static java.util.Objects.isNull;

@Slf4j
public class SeleniumTest extends TestBase {
    private static final String CHROME_DRIVER_PROPERTY_NAME = "webdriver.chrome.driver";
    private static final String CHROME_DRIVER_EXE_LOCATION = "chromedriver.exe";
    private static final boolean HEADLESS_MODE = true;

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
    public void stopDriver() {
        WebDriver driver = this.driver.get();
        if (!isNull(driver)) {
            log.info("Closing driver {}", driver);
            driver.close();
            driver.quit();
        }
    }

    private WebDriver extractDriver() {
        return Optional.ofNullable(driver.get())
            .orElseThrow(() -> new RuntimeException("WebDriver has not been initialized."));
    }
}

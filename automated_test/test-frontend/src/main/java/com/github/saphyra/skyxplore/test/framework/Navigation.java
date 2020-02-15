package com.github.saphyra.skyxplore.test.framework;

import org.openqa.selenium.WebDriver;

public class Navigation {
    public static void toIndexPage(WebDriver driver, int port) {
        VerifiedOperation.operate(
            new Operation() {
                @Override
                public void execute() {
                    driver.navigate().to(UrlProvider.getWebRoot(port));
                }

                @Override
                public boolean check() {
                    return driver.getCurrentUrl().equals(UrlProvider.getWebRoot(port));
                }
            },
            10,
            100
        );
    }
}

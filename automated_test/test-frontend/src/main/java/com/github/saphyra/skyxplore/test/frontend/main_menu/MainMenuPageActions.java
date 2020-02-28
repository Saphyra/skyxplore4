package com.github.saphyra.skyxplore.test.frontend.main_menu;

import static org.assertj.core.api.Assertions.assertThat;

import org.openqa.selenium.WebDriver;

import com.github.saphyra.skyxplore.app.common.config.RequestConstants;
import com.github.saphyra.skyxplore.test.framework.NotificationUtil;
import com.github.saphyra.skyxplore.test.framework.Operation;
import com.github.saphyra.skyxplore.test.framework.VerifiedOperation;

public class MainMenuPageActions {
    public static void logout(WebDriver driver) {
        assertThat(driver.getCurrentUrl()).endsWith("/main-menu");

        VerifiedOperation.operate(
            new Operation() {
                @Override
                public void execute() {
                    MainMenuPage.logoutButton(driver).click();
                }

                @Override
                public boolean check() {
                    return driver.getCurrentUrl().endsWith(RequestConstants.INDEX_MAPPING);
                }
            },
            10,
            100
        );

        NotificationUtil.verifySuccessNotification(driver, "Sikeres kijelentkezés.");
    }
}

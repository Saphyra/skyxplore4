package com.github.saphyra.skyxplore.test.frontend.index;

import com.github.saphyra.skyxplore.test.framework.Operation;
import com.github.saphyra.skyxplore.test.framework.UrlProvider;
import com.github.saphyra.skyxplore.test.framework.VerifiedOperation;
import com.github.saphyra.skyxplore.test.frontend.SeleniumTest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RedirectionTest extends SeleniumTest {
    @Test
    public void redirectToIndexWhenNoAccessToken() {
        //TODO
    }

    @Test
    public void redirectToWebWhenCalledRoot() {
        //GIVEN
        WebDriver driver = extractDriver();

        //WHEN
        Operation operation = new Operation() {
            @Override
            public void execute() {
                driver.navigate().to(UrlProvider.getRoot(PORT));
            }

            @Override
            public boolean check() {
                return driver.getCurrentUrl().equals(UrlProvider.getWebRoot(PORT));
            }
        };
        VerifiedOperation.operate(operation, 10, 100);
        //THEN
        assertThat(driver.getCurrentUrl()).isEqualTo(UrlProvider.getWebRoot(PORT));
    }
}

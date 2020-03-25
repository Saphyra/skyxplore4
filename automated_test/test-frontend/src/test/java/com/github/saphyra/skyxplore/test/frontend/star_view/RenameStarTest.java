package com.github.saphyra.skyxplore.test.frontend.star_view;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.github.saphyra.skyxplore.test.common.parameters.RegistrationParameters;
import com.github.saphyra.skyxplore.test.framework.Navigation;
import com.github.saphyra.skyxplore.test.framework.NotificationUtil;
import com.github.saphyra.skyxplore.test.framework.VerifiedOperation;
import com.github.saphyra.skyxplore.test.frontend.SeleniumTest;
import com.github.saphyra.skyxplore.test.frontend.index.IndexPageActions;
import com.github.saphyra.skyxplore.test.frontend.main_menu.MainMenuPageActions;
import com.github.saphyra.skyxplore.test.frontend.map_view.MapViewActions;
import com.github.saphyra.skyxplore.test.frontend.map_view.MapViewPage;

public class RenameStarTest extends SeleniumTest {

    private static final String GAME_NAME = "game-name";
    private static final String VALID_STAR_NAME = "valid-star name";

    @DataProvider(name = "invalidStarNames", parallel = true)
    public Object[] invalidStarNames() {
        return new Object[][]{
            new Object[]{"aa", "Név túl rövid (min. 3 karakter)."},
            new Object[]{Stream.generate(() -> "a").limit(31).collect(Collectors.joining()), "Név túl hosszú (max. 30 karakter)."}
        };
    }

    @Test(dataProvider = "invalidStarNames")
    public void invalidGameName(String starName, String errorMessage) {
        WebDriver driver = extractDriver();
        Navigation.toIndexPage(driver, PORT);

        RegistrationParameters user = RegistrationParameters.validParameters();
        IndexPageActions.registerUser(driver, user);

        MainMenuPageActions.createGame(driver, GAME_NAME);
        boolean result = VerifiedOperation.waitUntil(() -> !MapViewPage.getStars(driver).isEmpty());
        assertThat(result).isTrue();

        WebElement star = MapViewPage.getStars(driver).get(0);
        MapViewActions.openStar(driver, star);

        String originalStarName = StarViewPage.getStarNameField(driver).getText();
        StarViewActions.modifyStarName(driver, starName);
        NotificationUtil.verifyErrorNotification(driver, errorMessage);

        assertThat(StarViewPage.getStarNameField(driver).getText()).isEqualTo(originalStarName);

        StarViewActions.closePage(driver);
        WebElement starNameLabel = MapViewActions.getLabelForStar(driver, star);
        assertThat(starNameLabel.getText()).isEqualTo(originalStarName);

        MapViewActions.openStar(driver, star);
        assertThat(StarViewPage.getStarNameField(driver).getText()).isEqualTo(originalStarName);
    }

    @Test
    public void renameStar() {
        WebDriver driver = extractDriver();
        Navigation.toIndexPage(driver, PORT);

        RegistrationParameters user = RegistrationParameters.validParameters();
        IndexPageActions.registerUser(driver, user);

        MainMenuPageActions.createGame(driver, GAME_NAME);
        boolean result = VerifiedOperation.waitUntil(() -> !MapViewPage.getStars(driver).isEmpty());
        assertThat(result).isTrue();

        WebElement star = MapViewPage.getStars(driver).get(0);
        MapViewActions.openStar(driver, star);

        StarViewActions.modifyStarName(driver, VALID_STAR_NAME);
        NotificationUtil.verifySuccessNotification(driver, "Csillag átnevezve");

        assertThat(StarViewPage.getStarNameField(driver).getText()).isEqualTo(VALID_STAR_NAME);

        StarViewActions.closePage(driver);

        star = MapViewPage.getStars(driver).get(0);
        WebElement starNameLabel = MapViewActions.getLabelForStar(driver, star);
        assertThat(starNameLabel.getText()).isEqualTo(VALID_STAR_NAME);
        MapViewActions.openStar(driver, star);
        assertThat(StarViewPage.getStarNameField(driver).getText()).isEqualTo(VALID_STAR_NAME);
    }
}

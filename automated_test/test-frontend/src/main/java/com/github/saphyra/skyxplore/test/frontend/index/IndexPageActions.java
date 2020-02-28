package com.github.saphyra.skyxplore.test.frontend.index;

import static com.github.saphyra.skyxplore.test.common.TestBase.PORT;
import static com.github.saphyra.skyxplore.test.framework.WebElementUtils.clearAndFill;
import static org.assertj.core.api.Assertions.assertThat;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.saphyra.skyxplore.app.common.config.RequestConstants;
import com.github.saphyra.skyxplore.test.common.parameters.RegistrationParameters;
import com.github.saphyra.skyxplore.test.framework.NotificationUtil;
import com.github.saphyra.skyxplore.test.framework.Operation;
import com.github.saphyra.skyxplore.test.framework.UrlProvider;
import com.github.saphyra.skyxplore.test.framework.VerifiedOperation;
import com.github.saphyra.skyxplore.test.frontend.index.registration.PasswordValidationResult;
import com.github.saphyra.skyxplore.test.frontend.index.registration.RegistrationValidationResult;
import com.github.saphyra.skyxplore.test.frontend.index.registration.UsernameValidationResult;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Builder
@RequiredArgsConstructor
@Slf4j
class IndexPageActions {
    public static void registerUser(WebDriver driver, RegistrationParameters registrationParameters){
        VerifiedOperation.operate(
            new Operation() {
                @Override
                public void execute() {
                    IndexPageActions.fillRegistrationForm(driver, registrationParameters);
                    IndexPageActions.submitRegistration(driver);
                }

                @Override
                public boolean check() {
                    return driver.getCurrentUrl().endsWith("/main-menu");
                }
            }
        );

        assertThat(driver.getCurrentUrl()).isEqualTo(UrlProvider.getMainMenu(PORT));
        NotificationUtil.verifySuccessNotification(driver, "Sikeres regisztráció!");
    }

    static void fillRegistrationForm(WebDriver driver, RegistrationParameters parameters) {
        assertThat(driver.getCurrentUrl()).endsWith(RequestConstants.INDEX_MAPPING);

        log.info("Filling registrationForm with {}", parameters);
        clearAndFill(IndexPage.userNameInput(driver), parameters.getUserName());
        clearAndFill(IndexPage.passwordInput(driver), parameters.getPassword());
        clearAndFill(IndexPage.confirmPasswordInput(driver), parameters.getConfirmPassword());
    }

    static void verifyRegistrationForm(WebDriver driver, RegistrationValidationResult validationResult) {
        assertThat(driver.getCurrentUrl()).endsWith(RequestConstants.INDEX_MAPPING);

        verifyState(
            IndexPage.userNameValid(driver),
            validationResult.getUserName() != UsernameValidationResult.VALID,
            validationResult.getUserName().getErrorMessage()
        );

        verifyState(
            IndexPage.passwordValid(driver),
            validationResult.getPassword() != PasswordValidationResult.VALID,
            validationResult.getPassword().getErrorMessage()
        );

        verifyState(
            IndexPage.confirmPasswordValid(driver),
            validationResult.getConfirmPassword() != PasswordValidationResult.VALID,
            validationResult.getConfirmPassword().getErrorMessage()
        );
    }

    private static void verifyState(WebElement inputValid, boolean shouldBeVisible, String errorMessage) {
        if (shouldBeVisible) {
            assertThat(inputValid.isDisplayed()).isTrue();
            assertThat(inputValid.getAttribute("title")).isEqualTo(errorMessage);
        } else {
            assertThat(inputValid.isDisplayed()).isFalse();
        }
    }

    static void submitRegistration(WebDriver driver) {
        assertThat(driver.getCurrentUrl()).endsWith(RequestConstants.INDEX_MAPPING);
        WebElement submitButton = IndexPage.registrationSubmitButton(driver);

        VerifiedOperation.waitUntil(
            submitButton::isEnabled,
            3,
            1000
        );

        assertThat(submitButton.isEnabled()).isTrue();
        submitButton.click();
    }
}

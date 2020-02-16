package com.github.saphyra.skyxplore.test.frontend.index;

import com.github.saphyra.skyxplore.test.common.parameters.RegistrationParameters;
import com.github.saphyra.skyxplore.test.framework.Navigation;
import com.github.saphyra.skyxplore.test.framework.UrlProvider;
import com.github.saphyra.skyxplore.test.framework.VerifiedOperation;
import com.github.saphyra.skyxplore.test.frontend.SeleniumTest;
import com.github.saphyra.skyxplore.test.frontend.index.registration.PasswordValidationResult;
import com.github.saphyra.skyxplore.test.frontend.index.registration.RegistrationValidationResult;
import com.github.saphyra.skyxplore.test.frontend.index.registration.UsernameValidationResult;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RegistrationTest extends SeleniumTest {
    @DataProvider(name = "registrationParameters", parallel = true)
    public Object[][] registrationParameters() {
        return new Object[][]{
            {RegistrationParameters.validParameters(), valid()},
            {RegistrationParameters.tooShortUsernameParameters(), usernameTooShort()},
            {RegistrationParameters.tooLongUsernameParameters(), usernameTooLong()},
            {RegistrationParameters.tooShortPasswordParameters(), passwordTooShort()},
            {RegistrationParameters.tooLongPasswordParameters(), passwordTooLong()},
            {RegistrationParameters.incorrectConfirmPasswordParameters(), confirmPasswordIncorrect()}
        };
    }

    @Test(dataProvider = "registrationParameters")
    public void verifyValidation(RegistrationParameters parameters, RegistrationValidationResult validationResult) throws InterruptedException {
        //GIVEN
        WebDriver driver = extractDriver();
        Navigation.toIndexPage(driver, PORT);
        //WHEN
        IndexPageActions.fillRegistrationForm(driver, parameters);
        Thread.sleep(2000);
        //THEN
        IndexPageActions.verifyRegistrationForm(driver, validationResult);
    }

    @Test
    public void successfulRegistration() {
        //GIVEN
        WebDriver driver = extractDriver();
        Navigation.toIndexPage(driver, PORT);
        IndexPageActions.fillRegistrationForm(driver, RegistrationParameters.validParameters());

        //WHEN
        IndexPageActions.submitRegistration(driver);
        //THEN
        VerifiedOperation.waitUntil(
            () -> driver.getCurrentUrl().equals(UrlProvider.getMainMenu(PORT)),
            10,
            100
        );

        //THEN
        assertThat(driver.getCurrentUrl()).isEqualTo(UrlProvider.getMainMenu(PORT));
    }

    @Test
    public void userNameAlreadyExists() {
        //TODO
    }

    private RegistrationValidationResult valid() {
        return RegistrationValidationResult.builder()
            .userName(UsernameValidationResult.VALID)
            .password(PasswordValidationResult.VALID)
            .confirmPassword(PasswordValidationResult.VALID)
            .build();
    }

    private RegistrationValidationResult usernameTooShort() {
        return valid().toBuilder()
            .userName(UsernameValidationResult.TOO_SHORT)
            .build();
    }

    private RegistrationValidationResult usernameTooLong() {
        return valid().toBuilder()
            .userName(UsernameValidationResult.TOO_LONG)
            .build();
    }

    private RegistrationValidationResult passwordTooShort() {
        return valid().toBuilder()
            .password(PasswordValidationResult.TOO_SHORT)
            .build();
    }

    private RegistrationValidationResult passwordTooLong() {
        return valid().toBuilder()
            .password(PasswordValidationResult.TOO_LONG)
            .build();
    }

    private RegistrationValidationResult confirmPasswordIncorrect() {
        return valid().toBuilder()
            .confirmPassword(PasswordValidationResult.INVALID_CONFIRM_PASSWORD)
            .build();
    }
}

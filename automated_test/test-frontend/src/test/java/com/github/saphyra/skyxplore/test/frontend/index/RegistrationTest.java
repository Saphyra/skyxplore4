package com.github.saphyra.skyxplore.test.frontend.index;

import com.github.saphyra.skyxplore.test.framework.Navigation;
import com.github.saphyra.skyxplore.test.frontend.SeleniumTest;
import com.github.saphyra.skyxplore.test.frontend.index.registration.PasswordValidationResult;
import com.github.saphyra.skyxplore.test.frontend.index.registration.RegistrationParameters;
import com.github.saphyra.skyxplore.test.frontend.index.registration.RegistrationValidationResult;
import com.github.saphyra.skyxplore.test.frontend.index.registration.UsernameValidationResult;
import com.github.saphyra.util.IdGenerator;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RegistrationTest extends SeleniumTest {
    private static final String VALID_PASSWORD = "valid-password";
    private static final String TOO_SHORT_USERNAME = "to";
    private static final String TOO_SHORT_PASSWORD = "passw";
    private static final String INVALID_CONFIRM_PASSWORD = "invalid-confirm-password";

    private final IdGenerator idGenerator = new IdGenerator();

    @DataProvider(name = "registrationParameters", parallel = true)
    public Object[][] registrationParameters() {
        return new Object[][]{
            {validParameters(), valid()},
            {tooShortUsernameParameters(), usernameTooShort()},
            {tooLongUsernameParameters(), usernameTooLong()},
            {tooShortPasswordParameters(), passwordTooShort()},
            {tooLongPasswordParameters(), passwordTooLong()},
            {incorrectConfirmPasswordParameters(), confirmPasswordIncorrect()}
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
        //TODO
    }

    @Test
    public void userNameAlreadyExists() {
        //TODO
    }

    private RegistrationParameters validParameters() {
        String[] userNameCharacters = ("user-" + idGenerator.generateRandomId()).split("");
        String userName = Arrays.stream(userNameCharacters)
            .limit(30)
            .collect(Collectors.joining());
        return RegistrationParameters.builder()
            .userName(userName)
            .password(VALID_PASSWORD)
            .confirmPassword(VALID_PASSWORD)
            .build();
    }

    private RegistrationParameters tooShortUsernameParameters() {
        return validParameters().toBuilder()
            .userName(TOO_SHORT_USERNAME)
            .build();
    }

    private RegistrationParameters tooLongUsernameParameters() {
        String userName = Stream.generate(() -> "a")
            .limit(31)
            .collect(Collectors.joining());
        return validParameters().toBuilder()
            .userName(userName)
            .build();
    }

    private RegistrationParameters tooShortPasswordParameters() {
        return validParameters().toBuilder()
            .password(TOO_SHORT_PASSWORD)
            .confirmPassword(TOO_SHORT_PASSWORD)
            .build();
    }

    private RegistrationParameters tooLongPasswordParameters() {
        String password = Stream.generate(() -> "a")
            .limit(31)
            .collect(Collectors.joining());
        return validParameters().toBuilder()
            .password(password)
            .confirmPassword(password)
            .build();
    }

    private RegistrationParameters incorrectConfirmPasswordParameters() {
        return validParameters().toBuilder()
            .confirmPassword(INVALID_CONFIRM_PASSWORD)
            .build();
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

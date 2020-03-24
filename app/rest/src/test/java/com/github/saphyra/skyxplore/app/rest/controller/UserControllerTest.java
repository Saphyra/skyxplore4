package com.github.saphyra.skyxplore.app.rest.controller;

import com.github.saphyra.encryption.impl.PasswordService;
import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.exceptionhandling.exception.ConflictException;
import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.skyxplore.app.common.event.UserDeletedEvent;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContext;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContextHolder;
import com.github.saphyra.skyxplore.app.domain.user.domain.user.SkyXpUser;
import com.github.saphyra.skyxplore.app.domain.user.domain.user.UserRepository;
import com.github.saphyra.skyxplore.app.rest.controller.request.user.ChangePasswordRequest;
import com.github.saphyra.skyxplore.app.rest.controller.request.user.ChangeUsernameRequest;
import com.github.saphyra.skyxplore.app.rest.controller.request.user.DeleteAccountRequest;
import com.github.saphyra.skyxplore.app.rest.controller.request.user.RegistrationRequest;
import com.github.saphyra.skyxplore.app.common.exception_handling.ErrorCode;
import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;
import com.github.saphyra.util.IdGenerator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    private static final UUID USER_ID = UUID.randomUUID();
    private static final String ENTERED_PASSWORD = "entered-password";
    private static final String NEW_USER_NAME = "new-user-name";
    private static final ChangeUsernameRequest CHANGE_USERNAME_REQUEST = ChangeUsernameRequest.builder()
        .password(ENTERED_PASSWORD)
        .userName(NEW_USER_NAME)
        .build();
    private static final String USER_ID_STRING = "user-id";
    private static final String EXISTING_PASSWORD = "existing-password";
    private static final String NEW_PASSWORD = "new-password";
    private static final ChangePasswordRequest CHANGE_PASSWORD_REQUEST = ChangePasswordRequest.builder()
        .oldPassword(ENTERED_PASSWORD)
        .newPassword(NEW_PASSWORD)
        .build();
    private static final String HASHED_PASSWORD = "hashed-password";
    private static final DeleteAccountRequest DELETE_ACCOUNT_REQUEST = DeleteAccountRequest.builder()
        .password(ENTERED_PASSWORD)
        .build();
    private static final RegistrationRequest REGISTRATION_REQUEST = RegistrationRequest.builder()
        .userName(NEW_USER_NAME)
        .password(ENTERED_PASSWORD)
        .build();

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Mock
    private IdGenerator idGenerator;

    @Mock
    private RequestContextHolder requestContextHolder;

    @Mock
    private PasswordService passwordService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UuidConverter uuidConverter;

    @InjectMocks
    private UserController underTest;

    @Mock
    private RequestContext requestContext;

    @Mock
    private SkyXpUser user;

    @Before
    public void setUp() {
        given(requestContextHolder.get()).willReturn(requestContext);
        given(requestContext.getUserId()).willReturn(USER_ID);

        given(userRepository.findByUserName(NEW_USER_NAME)).willReturn(Optional.empty());
        given(uuidConverter.convertDomain(USER_ID)).willReturn(USER_ID_STRING);
        given(userRepository.findById(USER_ID_STRING)).willReturn(Optional.of(user));

        given(user.getPassword()).willReturn(EXISTING_PASSWORD);
        given(passwordService.authenticate(ENTERED_PASSWORD, EXISTING_PASSWORD)).willReturn(true);
    }

    @Test
    public void changePassword_userNotFound() {
        //GIVEN
        given(userRepository.findById(USER_ID_STRING)).willReturn(Optional.empty());
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.changePassword(CHANGE_PASSWORD_REQUEST));
        //THEN
        verifyUserNotFound(ex);
    }

    @Test
    public void changePassword_invalidPassword() {
        //GIVEN
        given(passwordService.authenticate(ENTERED_PASSWORD, EXISTING_PASSWORD)).willReturn(false);
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.changePassword(CHANGE_PASSWORD_REQUEST));
        //THEN
        verifyInvalidPassword(ex);
    }

    @Test
    public void changePassword() {
        //GIVEN
        given(passwordService.hashPassword(NEW_PASSWORD)).willReturn(HASHED_PASSWORD);
        //WHEN
        underTest.changePassword(CHANGE_PASSWORD_REQUEST);
        //THEN
        verify(user).setPassword(HASHED_PASSWORD);
        verify(userRepository).save(user);
    }

    @Test
    public void changeUsername_usernameAlreadyExists() {
        //GIVEN
        given(userRepository.findByUserName(NEW_USER_NAME)).willReturn(Optional.of(user));
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.changeUsername(CHANGE_USERNAME_REQUEST));
        //THEN
        verifyUsernameAlreadyExists(ex);
    }

    @Test
    public void changeUsername_userNotFound() {
        //GIVEN
        given(userRepository.findById(USER_ID_STRING)).willReturn(Optional.empty());
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.changeUsername(CHANGE_USERNAME_REQUEST));
        //THEN
        verifyUserNotFound(ex);
    }

    @Test
    public void changeUsername_invalidPassword() {
        //GIVEN
        given(passwordService.authenticate(ENTERED_PASSWORD, EXISTING_PASSWORD)).willReturn(false);
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.changeUsername(CHANGE_USERNAME_REQUEST));
        //THEN
        verifyInvalidPassword(ex);
    }

    @Test
    public void changeUsername() {
        //WHEN
        underTest.changeUsername(CHANGE_USERNAME_REQUEST);
        //THEN
        verify(user).setUserName(NEW_USER_NAME);
        verify(userRepository).save(user);
    }

    @Test
    public void deleteAccount_userNotFound() {
        //GIVEN
        given(userRepository.findById(USER_ID_STRING)).willReturn(Optional.empty());
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.deleteAccount(DELETE_ACCOUNT_REQUEST));
        //THEN
        verifyUserNotFound(ex);
    }

    @Test
    public void deleteAccount_invalidPassword() {
        //GIVEN
        given(passwordService.authenticate(ENTERED_PASSWORD, EXISTING_PASSWORD)).willReturn(false);
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.deleteAccount(DELETE_ACCOUNT_REQUEST));
        //THEN
        verifyInvalidPassword(ex);
    }

    @Test
    public void deleteAccount() {
        //GIVEN
        given(uuidConverter.convertEntity(USER_ID_STRING)).willReturn(USER_ID);
        given(user.getUserId()).willReturn(USER_ID_STRING);
        //WHEN
        underTest.deleteAccount(DELETE_ACCOUNT_REQUEST);
        //THEN
        verify(applicationEventPublisher).publishEvent(new UserDeletedEvent(USER_ID));
        verify(userRepository).delete(user);
    }

    @Test
    public void registerUser_usernameAlreadyExists() {
        //GIVEN
        given(userRepository.findByUserName(NEW_USER_NAME)).willReturn(Optional.of(user));
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.registerUser(REGISTRATION_REQUEST));
        //THEN
        verifyUsernameAlreadyExists(ex);
    }

    @Test
    public void registerUser() {
        //GIVEN
        given(idGenerator.generateRandomId()).willReturn(USER_ID_STRING);
        given(passwordService.hashPassword(ENTERED_PASSWORD)).willReturn(HASHED_PASSWORD);
        //WHEN
        underTest.registerUser(REGISTRATION_REQUEST);
        //THEN
        ArgumentCaptor<SkyXpUser> argumentCaptor = ArgumentCaptor.forClass(SkyXpUser.class);
        verify(userRepository).save(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue().getUserId()).isEqualTo(USER_ID_STRING);
        assertThat(argumentCaptor.getValue().getUserName()).isEqualTo(NEW_USER_NAME);
        assertThat(argumentCaptor.getValue().getPassword()).isEqualTo(HASHED_PASSWORD);
    }

    private void verifyInvalidPassword(Throwable ex) {
        assertThat(ex).isInstanceOf(BadRequestException.class);
        BadRequestException exception = (BadRequestException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.INVALID_PASSWORD.name());
    }

    private void verifyUserNotFound(Throwable ex) {
        assertThat(ex).isInstanceOf(NotFoundException.class);
        NotFoundException exception = (NotFoundException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.USER_NOT_FOUND.name());
    }

    private void verifyUsernameAlreadyExists(Throwable ex) {
        assertThat(ex).isInstanceOf(ConflictException.class);
        ConflictException exception = (ConflictException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.USER_NAME_ALREADY_EXISTS.name());
    }
}
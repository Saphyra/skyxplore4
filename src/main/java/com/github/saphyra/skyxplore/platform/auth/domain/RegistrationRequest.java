package com.github.saphyra.skyxplore.platform.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RegistrationRequest {
    public static final int USER_NAME_MAX_LENGTH = 30;
    public static final int USER_NAME_MIN_LENGTH = 3;
    public static final int PASSWORD_MAX_LENGTH = 30;
    public static final int PASSWORD_MIN_LENGTH = 6;

    @NotNull
    @Size(min = USER_NAME_MIN_LENGTH, max = USER_NAME_MAX_LENGTH)
    private String userName;

    @NotNull
    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;
}

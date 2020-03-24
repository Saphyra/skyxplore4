package com.github.saphyra.skyxplore.app.rest.controller.request.user;

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
public class ChangePasswordRequest {
    public static final int PASSWORD_MAX_LENGTH = 30;
    public static final int PASSWORD_MIN_LENGTH = 6;

    @NotNull
    private String oldPassword;

    @NotNull
    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String newPassword;
}
